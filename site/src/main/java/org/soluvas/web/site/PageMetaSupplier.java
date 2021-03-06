package org.soluvas.web.site;

import org.apache.wicket.Page;
import org.soluvas.web.site.pagemeta.PageMeta;

import com.google.common.base.Supplier;

/**
 * Supplies a {@link Page} metadata to be rendered in a web page template.
 * @author ceefour
 */
public interface PageMetaSupplier extends Supplier<PageMeta> {

}
