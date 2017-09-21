// JWR: This modification gets rid of nasty problems to do with unroutable hostnames.
// OSX uses these a lot, and it is a total pain.
// See my comments in stdlib.Trace.java
//
// In openjdk8, https://jdk8.java.net , this source is found in
//
//   openjdk8/jdk/src/share/classes/com/sun/tools/jdi/SocketTransportService.java

/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.tools.jdi;

import java.net.*;

class SocketTransportService$SocketListenKey extends com.sun.jdi.connect.spi.TransportService.ListenKey {
	ServerSocket ss;
	SocketTransportService$SocketListenKey (ServerSocket ss) {
		this.ss = ss;
	}
	ServerSocket socket () {
		return ss;
	}
	public String toString () {
		return address ();
	}

	// Returns the string representation of the address that this listen key represents.
	public String address () {
		InetAddress address = ss.getInetAddress ();

		// If bound to the wildcard address then use current local hostname. In
		// the event that we don't know our own hostname then assume that host
		// supports IPv4 and return something to represent the loopback address.
		if (address.isAnyLocalAddress ()) {
			// JWR: Only change is to comment out the lines below
			// try {
			//     address = InetAddress.getLocalHost ();
			// } catch (UnknownHostException uhe) {
			byte[] loopback = { 0x7f, 0x00, 0x00, 0x01 };
			try {
				address = InetAddress.getByAddress ("127.0.0.1", loopback);
			} catch (UnknownHostException x) {
				throw new InternalError ("unable to get local hostname");
			}
			//  }
		}

		// Now decide if we return a hostname or IP address. Where possible
		// return a hostname but in the case that we are bound to an address
		// that isn't registered in the name service then we return an address.
		String result;
		String hostname = address.getHostName ();
		String hostaddr = address.getHostAddress ();
		if (hostname.equals(hostaddr)) {
			if (address instanceof Inet6Address) {
				result = "[" + hostaddr + "]";
			} else {
				result = hostaddr;
			}
		} else {
			result = hostname;
		}

		// Finally return "hostname:port", "ipv4-address:port" or "[ipv6-address]:port".
		return result + ":" + ss.getLocalPort ();
	}
}