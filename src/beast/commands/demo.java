package beast.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import beast.app.shell.BEASTStudio;
import bsh.Interpreter;
import beast.app.shell.Plot;
import beast.util.AddOnManager;
import bsh.CallStack;

public class demo {
	final static String DEMO_DIR = "examples/demo/";

	public static String usage() {
		return "usage: demo(demoname?)\n       demo()\n        demo(\"chart\")";
	}

	/**
	 * Implement demo() command.
	 */
	public static void invoke(Interpreter env, CallStack callstack) {
		List<String> demos = new ArrayList<String>();
		File file = new File(DEMO_DIR);
		if (file.exists()) {
			collectDemos(file, demos);
		} else {
			file = new File(AddOnManager.getPackageUserDir() + BEASTStudio.PACKAGENAME + DEMO_DIR);
			collectDemos(file, demos);
		}
		for (String msg : demos) {
			env.println(msg);
		}
	}

	private static void collectDemos(File file, List<String> demos) {
		if (file.exists()) {
			for (File f : file.listFiles()) {
				if (f.getName().toLowerCase().endsWith(".bsh")) {
					// get first line
					try {
						BufferedReader fin = new BufferedReader(new FileReader(f));
						String description = fin.readLine();
						fin.close();
						String name = f.getName();
						name = name.substring(0, name.length() - 4);
						description = description.replaceFirst("//", "");
						demos.add(name + ": " + description);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	/**
	 * Implement demo( String name) command.
	 */
	public static void invoke(Interpreter env, CallStack callstack, String file) {
		String fileBsh = file + ".bsh";
		if (!(new File(file)).exists() && !(new File(fileBsh)).exists()) {
			file = DEMO_DIR + file;
			fileBsh = DEMO_DIR + fileBsh;
			if (!(new File(file)).exists() && !(new File(fileBsh)).exists()) {
				file = AddOnManager.getPackageUserDir() + BEASTStudio.PACKAGENAME + file;
				fileBsh = AddOnManager.getPackageUserDir() + BEASTStudio.PACKAGENAME + fileBsh;
			}
		}
		if (!(new File(file)).exists()) {
			file = fileBsh;
		}
		try {
			BufferedReader fin = new BufferedReader(new FileReader(file));
			String buf = "";
			while (fin.ready()) {
				String str = fin.readLine();
				env.println(str);
				if (str.indexOf("pause();") >= 0) {
					env.eval(buf);
					if (JOptionPane.showConfirmDialog(Plot.studio.editorPanel, "Continue?", "Demo " + file, JOptionPane.YES_NO_OPTION) != 0){
						return;
					}
					buf = "";
				} else {
					buf = buf + str + "\n";
				}
			}
			fin.close();
			env.eval(buf);
		} catch (Exception e) {
			env.println(e.getMessage());
		}
	}

}
