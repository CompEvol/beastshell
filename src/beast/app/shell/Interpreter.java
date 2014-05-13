/*****************************************************************************
 *                                                                           *
 *  This file is part of the BeanShell Java Scripting distribution.          *
 *  Documentation and updates may be found at http://www.beanshell.org/      *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    * 
 *                                                                           *
 *  The Original Code is BeanShell. The Initial Developer of the Original    *
 *  Code is Pat Niemeyer. Portions created by Pat Niemeyer are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  Patrick Niemeyer (pat@pat.net)                                           *
 *  Author of Learning Java, O'Reilly & Associates                           *
 *  http://www.pat.net/~pat/                                                 *
 *                                                                           *
 *****************************************************************************/

package beast.app.shell;

import java.io.InputStream;
import java.io.PrintStream;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import bsh.BshScriptEngineFactory;
import bsh.CallStack;
import bsh.EvalError;
import bsh.InterpreterError;
import bsh.NameSpace;
import bsh.ParseException;
import bsh.Primitive;
import bsh.TargetError;
import bsh.TokenMgrError;
import bsh.util.JConsole;

public class Interpreter 
{
	public BEASTStudio studio = null;
	//bsh.Interpreter interpreter;
	
	ScriptEngine interpreter;
	JConsole console;

	PrintStream err;
	PrintStream out;
	
	public Interpreter(JConsole console, BEASTStudio studio, String engineName) {
        ScriptEngineManager factory = new ScriptEngineManager();
        interpreter = engineName.equals("beastshell") ? 
        		new BshScriptEngineFactory().getScriptEngine() : 
        		factory.getEngineByName(engineName);
		//interpreter = new bsh.Interpreter(console);
		this.studio = studio;
		this.console = console;
		//interpreter.studio = studio;
		err = console.getErr();
		out = console.getOut();
		console.interpreter = interpreter;
		
		System.setErr(err);
		System.setOut(out);
		try {
			interpreter.eval("printBanner()");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object get(String name) throws EvalError {
		return interpreter.get(name);
	}

	public PrintStream getErr() {
		return console.getErr();
	}

	public void run() {
		InputStream in = console.getInputStream();
		while (true) {
			try {
				byte c = (byte) in.read();
				int len = in.available();
				byte [] bytes = new byte[len + 1];
				bytes[0] = c;
				in.read(bytes, 1, len);
				String s = new String(bytes);
				interpreter.eval(s);
				studio.update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Bindings getNameSpace() {
		return interpreter.getBindings(ScriptContext.ENGINE_SCOPE);
	}
	
}