package se.pj.tbike.api.util;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Error {

	/**
	 * Is a message in case the passed value is greater than the maximum
	 * requested value. It has two parameters:
	 * <ul>
	 *   <li>
	 *     The first parameter is the name of the object whose value is passed
	 *     in.
	 *   </li>
	 *   <li>
	 *     The second parameter is the maximum limit value.
	 *   </li>
	 * </ul>
	 */
	GREATER_THAN( "The value of %s cannot be greater than %s." ),

	/**
	 * Is a message in case the passed value is incorrect. It has only one
	 * parameter:
	 * <ul>
	 *   <li>
	 *       The only parameter is the name of the object with the incorrect
	 *       value.
	 *   </li>
	 * </ul>
	 */
	INVALID( "The value of %s is an invalid value." ),

	/**
	 * Is the message for the case of comparing two unequal values. There are
	 * two types of messages with one to two parameters:
	 * <ul>
	 *  <li>
	 *      The first parameter is the expected value.
	 *  </li>
	 *  <li>
	 *      <strong>(optional)</strong> The second parameter is the current
	 *      value.
	 *  </li>
	 * </ul>
	 */
	NOT_EQUAL(
			"No value equals %s.",
			"%s is the requested value, but the current value is %s."
	),

	/**
	 * Is a message for the case where no object is found with a value matching
	 * the passed value. It has two parameters:
	 * <ul>
	 *  <li>
	 *      The first parameter is the name of the object whose value is passed
	 *      in.
	 *  </li>
	 *  <li>
	 *      The second parameter is the value passed in.
	 *  </li>
	 * </ul>
	 */
	NOT_FOUND( "Object %s with value %s does not exist." ),

	/**
	 * Is a message for the case where the passed value is null but a non-null
	 * value is required. It has only one parameter:
	 * <ul>
	 *  <li>
	 *      The only parameter is the name of the object with a non-null value.
	 *  </li>
	 * </ul>
	 */
	NULL( "The %s value is required." ),

	/**
	 * Is the message when an object cannot be converted to a number. It has
	 * only one parameter:
	 * <ul>
	 *  <li>
	 *      The only parameter is the value passed in.
	 *  </li>
	 * </ul>
	 */
	NaN( "The value %s is not a number." ),

	/**
	 * Is a message in case the passed value is less than the minimum required
	 * value. It has two parameters:
	 * <ul>
	 *   <li>
	 *       The first parameter is the name of the object whose value is passed
	 *       in.
	 *   </li>
	 *   <li>
	 *       The second parameter is the limit value.
	 *   </li>
	 * </ul>
	 */
	SMALLER_THAN( "The value of %s cannot be less than %s." ),
	;

	private static final String TEMPLATE_PARAMETER_REG_EX =
			"%(\\d+\\$)?([-#+ 0,(<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

	/**
	 * Number of arguments in template - template
	 */
	private final Map<Integer, String> templates;

	Error( String... templates ) {
		if ( templates == null )
			throw new NullPointerException();
		if ( templates.length < 1 )
			throw new IllegalArgumentException();

		Pattern pattern = Pattern.compile( TEMPLATE_PARAMETER_REG_EX );
		int size = templates.length;
		@SuppressWarnings( "unchecked" )
		Entry<Integer, String>[] entries = new Entry[size];
		for ( int i = 0; i < size; i++ ) {
			String template = templates[i];
			entries[i] =
					entry( getArgumentCount( pattern, template ), template );
		}
		this.templates = Map.ofEntries( entries );
	}

	private int getArgumentCount( Pattern pattern, String s ) {
		Matcher matcher = pattern.matcher( s );
		int count = 0;
		while ( matcher.find() )
			count++;
		return count;
	}

	public String getMessage( Object... objects ) {
		if ( objects == null )
			throw new NullPointerException();
		String template = templates.get( objects.length );
		return template == null
				? null
				: template.formatted( objects );
	}
}
