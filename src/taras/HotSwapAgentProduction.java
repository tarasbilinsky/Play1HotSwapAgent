package taras;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public final class HotSwapAgentProduction {

    private static Instrumentation instrumentation;
    
    public static final String logLocation = "/usr/share/tomcat7/logs/HotSwapAgentProduction.log";
    
    public static final long ts = System.currentTimeMillis();

    static {
    	FileWriter w =  null;
		try {
			w = new FileWriter(logLocation, true);
		} catch (IOException e) {}
		PrintWriter p = w!=null? new PrintWriter(w):null;
    	if(p!=null){
    		p.println("Created Class "+ts);
    	}
    	try {
			w.close();
		} catch (IOException e) {}
    }
    
    static void setInstr(Instrumentation instrumentation){
    	FileWriter w =  null;
		try {
			w = new FileWriter(logLocation, true);
		} catch (IOException e) {}
		PrintWriter p = w!=null? new PrintWriter(w):null;
    	if(p!=null){
    		p.println("Set Instrumentation "+(instrumentation==null?"null":instrumentation.toString()));
    	}
    	HotSwapAgentProduction.instrumentation = instrumentation;
    	try {
			w.close();
		} catch (IOException e) {}
    }

    public static final void premain(String agentArgs, Instrumentation instrumentation) {
    	FileWriter w =  null;
		try {
			w = new FileWriter(logLocation, true);
		} catch (IOException e) {}
		PrintWriter p = w!=null? new PrintWriter(w):null;
    	if(p!=null){
    		p.println("Class Loader Class "+HotSwapAgentProduction.class.getClassLoader().getClass().getName());
    		p.println("Class Loader Obj "+HotSwapAgentProduction.class.getClassLoader().toString());
    	}
    	try {
			w.close();
		} catch (IOException e) {}
    	setInstr(instrumentation);
    }

    public static final void reload(ClassDefinition definition) {
    	FileWriter w =  null;
		try {
			w = new FileWriter(logLocation, true);
		} catch (IOException e) {}
		PrintWriter p = w!=null? new PrintWriter(w):null;
    	if(p!=null){
    		p.println("Trying to reload "+(instrumentation==null?"null":instrumentation.toString()));
    		p.println("ts="+ts);
    	}
    	ClassDefinition[] cds = new ClassDefinition[1];
    	cds[0] = definition;
    	try{
    		instrumentation.redefineClasses(cds);
    	} catch (Exception e){
    		if(p!=null){
    			p.println("instrumentation.redefineClasses");
    			p.println("ts="+ts);
    			e.printStackTrace(p);
    		}    		
    	}
    	try {
			w.close();
		} catch (IOException e) {}
    }
}