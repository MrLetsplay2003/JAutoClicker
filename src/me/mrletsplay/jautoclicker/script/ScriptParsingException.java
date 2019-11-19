package me.mrletsplay.jautoclicker.script;

import me.mrletsplay.mrcore.misc.FriendlyException;

public class ScriptParsingException extends FriendlyException {

	private static final long serialVersionUID = 6985929533874346195L;

	public ScriptParsingException(String reason) {
		super(reason);
	}

}
