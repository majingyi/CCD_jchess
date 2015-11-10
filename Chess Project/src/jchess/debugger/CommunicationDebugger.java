package jchess.debugger;

import java.io.IOException;
import java.util.List;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Method;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

public class CommunicationDebugger {

	public static void main(String[] args) {

		try {
			VMAcquierer acq = new VMAcquierer();
			VirtualMachine vm = acq.connect(8000);

			addMethodWatch(vm);

			// process events
			EventQueue eventQueue = vm.eventQueue();
			while (true) {
				EventSet eventSet = eventQueue.remove();

				for (Event event : eventSet) {
					if (event instanceof MethodEntryEvent) {
						MethodEntryEvent entryEvent = (MethodEntryEvent) event;
						Method m = entryEvent.location().method();
						if (m.name().startsWith("send")) {
							List<LocalVariable> vars = m.arguments();
							for (LocalVariable var : vars) {
								System.out.println("Argument: " + var.name());

								StackFrame stackFrame = entryEvent.thread().frame(0);
								Value value = stackFrame.getValue(var);
								String val = "unknown";
								if (value instanceof BooleanValue) {
									val = Boolean.toString(((BooleanValue) value).value());
								} else if (value instanceof IntegerValue) {
									val = Integer.toString(((IntegerValue) value).value());
								}

								System.out.println("Value: " + val);
							}
						}
					}
				}

				vm.resume();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (AbsentInformationException e) {
			e.printStackTrace();
		} catch (IncompatibleThreadStateException e) {
			e.printStackTrace();
		}
	}

	/** Watch all classes of name "Test" */
	private static void addMethodWatch(VirtualMachine vm) {
		EventRequestManager erm = vm.eventRequestManager();
		MethodEntryRequest methodEntryRequest = erm.createMethodEntryRequest();
		methodEntryRequest.addClassFilter("jchess.server.Table");
		methodEntryRequest.setEnabled(true);
	}
}
