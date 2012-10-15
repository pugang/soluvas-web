package org.soluvas.web.site.osgi;

import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.soluvas.web.site.FederatingPageRulesSupplier;
import org.soluvas.web.site.PageRule;
import org.soluvas.web.site.PageRulesSupplier;

import com.google.common.base.Supplier;

/**
 * Monitors {@link PageRulesSupplier} instances in a {@link FederatingPageRulesSupplier}.
 * 
 * Usage:
 *
 * <pre>
 *     <bean id="federatingPageRulesSupplier" class="org.soluvas.web.site.FederatingPageRulesSupplier">
 *     	<argument>
 *     		<list>
 *     			<ref component-id="defaultPageRulesSupplier" />
 *     		</list>
 *     	</argument>
 *     </bean>
 *     <bean class="org.osgi.util.tracker.ServiceTracker" init-method="open" destroy-method="close">
 *     	<argument ref="blueprintBundleContext" />
 *     	<argument value="org.soluvas.web.site.PageRulesSupplier" />
 *     	<argument>
 *     		<bean class="org.soluvas.web.site.osgi.PageRulesSupplierTracker">
 *     			<argument ref="federatingPageRulesSupplier" />
 *     		</bean>
 *     	</argument>
 *     </bean>
 * </pre>
 *   
 * Registering a page rules supplier:
 * 
 * <pre>
 * 	<service auto-export="interfaces">
 * 	    <bean class="org.soluvas.web.site.XmiPageRulesSupplier">
 * 	    	<argument value="id.co.bippo.app.AppSessionUtils" />
 * 	    	<argument value="/id/co/bippo/app/berbatik.pagerules.xmi" />
 * 	    </bean>
 *     </service>
 * </pre>
 * 
 * @author ceefour
 */
public class PageRulesSupplierTracker implements ServiceTrackerCustomizer {

	private FederatingPageRulesSupplier federator;
	
	public PageRulesSupplierTracker(FederatingPageRulesSupplier federator) {
		super();
		this.federator = federator;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		PageRulesSupplier supplier = reference.getBundle().getBundleContext().getService(reference);
		federator.add(supplier);
		return supplier;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#modifiedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void modifiedService(ServiceReference reference, Object service) {
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		federator.remove((Supplier<List<PageRule>>) service);
		reference.getBundle().getBundleContext().ungetService(reference);
	}

}