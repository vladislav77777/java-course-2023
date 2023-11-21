package edu.hw6;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("checkstyle:magicnumber")
public final class Task6 {

    private static final String TCP = "TCP";
    private static final String UDP = "UDP";
    private static final String N_A = "N/A";

    private Task6() {
    }

    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 49151;

    public static String scanPorts() {
        StringBuilder out = new StringBuilder();

        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            out.append(scanPort(TCP, port));
            out.append(scanPort(UDP, port));
        }
        return out.toString();
    }

    public static String scanPort(String protocol, int port) {
        try {
            InetAddress address = InetAddress.getByName("localhost");
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);

            if (protocol.equals(TCP)) {
                try (ServerSocket serverSocket = new ServerSocket()) {
                    serverSocket.bind(socketAddress);
                    String service = getKnownService(port);
                    if (!service.equals("")) {
                        return printResult(protocol, port, service);
                    }
                }
            } else {
                try (DatagramSocket ignore = new DatagramSocket(socketAddress)) {
                    String service = getKnownService(port);
                    if (!service.equals("")) {
                        return printResult(protocol, port, service);
                    }
                }
            }
        } catch (Exception e) {
            // Port is already in use
        }
        return "";
    }

    public static String printResult(String protocol, int port, String service) {
        return String.format("%-9s%-7d%-30s\n", protocol, port, service);
    }

    public static String getKnownService(int port) {
        // Comparison of known ports with their services
        Map<Integer, String> knownServices = new HashMap<>();
        knownServices.put(135, "EPMAP");
        knownServices.put(137, "Служба имен NetBIOS");
        knownServices.put(138, "Служба датаграмм NetBIOS");
        knownServices.put(139, "Служба сеансов NetBIOS");
        knownServices.put(445, "Microsoft-DS Active Directory");
        knownServices.put(843, "Adobe Flash");
        knownServices.put(1900, "Simple Service Discovery Protocol (SSDP)");
        knownServices.put(3702, "Динамическое обнаружение веб-служб");
        knownServices.put(5353, "Многоадресный DNS");
        knownServices.put(5355, "Link-Local Multicast Name Resolution (LLMNR)");
        knownServices.put(17500, "Dropbox");
        knownServices.put(80, "HTTP");
        knownServices.put(21, "FTP");
        knownServices.put(25, "SMTP");
        knownServices.put(22, "SSH");
        knownServices.put(443, "HTTPS");
        knownServices.put(53, "DNS");
        knownServices.put(3306, "MySQL Database");
        knownServices.put(5432, "PostgreSQL Database");
        knownServices.put(3389, "Remote Desktop Protocol (RDP)");
        knownServices.put(27017, "MongoDB Database");
        knownServices.put(1521, "Oracle Database");
        knownServices.put(42420, N_A);
        knownServices.put(6463, N_A);
        knownServices.put(5939, N_A);
        knownServices.put(6942, N_A);

        return knownServices.getOrDefault(port, "");
    }
}
