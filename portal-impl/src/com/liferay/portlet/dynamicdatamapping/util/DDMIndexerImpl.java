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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class DDMIndexerImpl implements DDMIndexer {

	public void addAttributes(
		Document document, DDMStructure ddmStructure, Fields fields) {

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				for (Locale locale : fields.getAvailableLocales()) {
					String name = encodeName(
						ddmStructure.getStructureId(), field.getName(), locale);

					Serializable value = field.getValue(locale);

					if (value instanceof Boolean) {
						document.addKeyword(name, (Boolean)value);
					}
					else if (value instanceof Boolean[]) {
						document.addKeyword(name, (Boolean[])value);
					}
					else if (value instanceof Date) {
						document.addDate(name, (Date)value);
					}
					else if (value instanceof Date[]) {
						document.addDate(name, (Date[])value);
					}
					else if (value instanceof Double) {
						document.addKeyword(name, (Double)value);
					}
					else if (value instanceof Double[]) {
						document.addKeyword(name, (Double[])value);
					}
					else if (value instanceof Integer) {
						document.addKeyword(name, (Integer)value);
					}
					else if (value instanceof Integer[]) {
						document.addKeyword(name, (Integer[])value);
					}
					else if (value instanceof Object[]) {
						String[] valuesString = ArrayUtil.toStringArray(
							(Object[])value);

						if (indexType.equals("keyword")) {
							document.addKeyword(name, valuesString);
						}
						else {
							document.addText(name, valuesString);
						}
					}
					else {
						String valueString = String.valueOf(value);

						if (indexType.equals("keyword")) {
							document.addKeyword(name, valueString);
						}
						else {
							document.addText(name, valueString);
						}
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	public String encodeName(long ddmStructureId, String fieldName) {
		return encodeName(ddmStructureId, fieldName, null);
	}

	public String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		StringBundler sb = new StringBundler(7);

		sb.append(_FIELD_NAMESPACE);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmStructureId);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(fieldName);

		if (Validator.isNotNull(locale)) {
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));
		}

		return sb.toString();
	}

	private static final String _FIELD_NAMESPACE = "ddm";

	private static Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

}