/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.socialofficeprofile.profile.sousremoveconnectionccuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_RemoveConnectionCCUserProfileTest extends BaseTestCase {
	public void testSOUs_RemoveConnectionCCUserProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible(
			"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_contactsportlet_name']"));
		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.waitForText("//div[contains(@class, 'lfr-contact-name')]/a",
			"Bloggs, Joe");
		assertEquals(RuntimeVariables.replace("Bloggs, Joe"),
			selenium.getText("//div[contains(@class, 'lfr-contact-name')]/a"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[contains(@class, 'lfr-contact-extra')]"));
		Thread.sleep(1000);
		selenium.clickAt("//div[contains(@class, 'lfr-contact-name')]/a",
			RuntimeVariables.replace("Bloggs, Joe"));
		selenium.waitForVisible("//div[contains(@class, 'contacts-profile')]");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		selenium.clickAt("//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"xPath=(//div[@class='lfr-contact-name'])[contains(.,'Joe Bloggs')]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Remove Connection"),
			selenium.getText("//span[@class='action remove-connection']/a"));
		selenium.clickAt("//span[@class='action remove-connection']/a",
			RuntimeVariables.replace("Remove Connection"));
		selenium.waitForVisible("//span[@class='action add-connection']/a");
		assertEquals(RuntimeVariables.replace("Add Connection"),
			selenium.getText("//span[@class='action add-connection']/a"));
	}
}