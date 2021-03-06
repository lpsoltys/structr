/**
 * Copyright (C) 2010-2016 Structr GmbH
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.core.function;

import org.apache.commons.lang3.RandomStringUtils;
import org.structr.common.error.FrameworkException;
import org.structr.core.GraphObject;
import org.structr.schema.action.ActionContext;
import org.structr.schema.action.Function;

/**
 *
 */
public class RandomFunction extends Function<Object, Object> {

	public static final String ERROR_MESSAGE_RANDOM = "Usage: ${random(num)}. Example: ${set(this, \"password\", random(8))}";

	@Override
	public String getName() {
		return "random()";
	}

	@Override
	public Object apply(final ActionContext ctx, final GraphObject entity, final Object[] sources) throws FrameworkException {

		try {
		
			if (!(arrayHasLengthAndAllElementsNotNull(sources, 1) && sources[0] instanceof Number)) {
				
				return null;
			}

			try {

				return RandomStringUtils.randomAlphanumeric(((Number)sources[0]).intValue());

			} catch (Throwable t) {

				logException(entity, t, sources);

			}

		} catch (final IllegalArgumentException e) {

			logParameterError(entity, sources, ctx.isJavaScriptContext());

			return usage(ctx.isJavaScriptContext());

		}

		return "";
	}


	@Override
	public String usage(boolean inJavaScriptContext) {
		return ERROR_MESSAGE_RANDOM;
	}

	@Override
	public String shortDescription() {
		return "Returns a random alphanumeric string of the given length";
	}

}
