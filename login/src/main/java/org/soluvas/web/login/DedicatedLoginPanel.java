package org.soluvas.web.login;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soluvas.commons.WebAddress;
import org.soluvas.data.EntityLookup;
import org.soluvas.ldap.Person;
import org.soluvas.web.login.facebook.FacebookLoginLink;
import org.soluvas.web.login.google.GoogleLoginLink;
import org.soluvas.web.login.twitter.TwitterLoginLink;
import org.soluvas.web.site.SoluvasWebSession;

/**
 * @author rudi
 *
 */
@SuppressWarnings("serial")
public class DedicatedLoginPanel extends Panel {
	
	static final Logger log = LoggerFactory
			.getLogger(DedicatedLoginPanel.class);

	public DedicatedLoginPanel(@Nonnull final String id,
			@Nonnull final IModel<LoginFormModel> userLoginModel) {
		super(id, userLoginModel);
		
		add(new FormSignIn("formSignIn", userLoginModel, this));
	}
	
	public class FormSignIn extends Form<LoginFormModel> {
		
		@SpringBean
		private WebAddress webAddress;
		@SpringBean(name="personLookup")
		private EntityLookup<Person, String> personLookup;
		
		@SuppressWarnings("unused")
		public FormSignIn(@Nonnull final String id, @Nonnull final IModel<LoginFormModel> userLoginModel,
				@Nonnull final Component dedicatedLoginPanelComponent) {
			super(id, userLoginModel);
			
			setOutputMarkupId(true);
			
			add(new TextField<String>("userName", new PropertyModel<String>(userLoginModel, "username")));
			add(new PasswordTextField("password", new PropertyModel<String>(userLoginModel, "password")));
			add(new CheckBox("rememberMe", new PropertyModel<Boolean>(userLoginModel, "rememberMe")));
			
			final LdapLoginButton ldapLoginBtn = new LdapLoginButton("login", userLoginModel) {
				@Override
				protected void onLoginSuccess(AjaxRequestTarget target, String personId) {
					final SoluvasWebSession solWebSession = (SoluvasWebSession) getSession();
					if (solWebSession.getOriginalUrl() != null) {
						final String destUri = webAddress.getBaseUri() + solWebSession.getOriginalUrl().toString();
						solWebSession.setOriginalUrl(null);
						log.debug("Session has originalUrl, redirecting to {}", destUri);
						throw new RedirectToUrlException(destUri);
					} else {
//						final String redirectUri = new RedirectByUserType(mallManager, webAddress, shopRepo, personLookup).apply(personId);
						final String destUri = webAddress.getBaseUri() + solWebSession.getOriginalUrl().toString();
						log.debug("Redirecting  by usertype to {}", destUri);
						throw new RedirectToUrlException(destUri);
//						final Class<? extends Page> homePage = Application.get().getHomePage();
//						log.debug("Session has no, redirecting to {}", homePage.getName()); 
//						throw new RestartResponseException(homePage);
					}
				}
			};
			add(ldapLoginBtn);
			
			add(new FacebookLoginLink("btnLoginWithFb"));
			add(new TwitterLoginLink("btnLoginWithTwitter"));
			final GoogleLoginLink googleLoginLink = new GoogleLoginLink("btnLoginWithGoogle");
			// TODO: enable Google when it's actually working with Google accounts, not Google+
			googleLoginLink.setVisible(false);
			add(googleLoginLink);
			
			setDefaultButton(ldapLoginBtn);
		}
		
	}

}
