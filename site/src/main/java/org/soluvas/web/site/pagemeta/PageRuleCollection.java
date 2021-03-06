/**
 */
package org.soluvas.web.site.pagemeta;

import org.eclipse.emf.common.util.EList;

import org.soluvas.commons.SerializableEObject;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Rule Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Contains a list of rules.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.soluvas.web.site.pagemeta.PageRuleCollection#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.soluvas.web.site.pagemeta.PagemetaPackage#getPageRuleCollection()
 * @model interface="true" abstract="true"
 * @extends SerializableEObject
 * @generated
 */
public interface PageRuleCollection extends SerializableEObject {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.soluvas.web.site.pagemeta.PageRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.soluvas.web.site.pagemeta.PagemetaPackage#getPageRuleCollection_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<PageRule> getRules();

} // PageRuleCollection
