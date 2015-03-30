/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package eu.matejkormuth.rpgdavid.starving.remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private ReaderThread readerThread;
    private ConnectionCallback onLine;

    public Connection(Socket socketConnection) throws IOException {
        this.socket = socketConnection;
        // Set timeout, to stop connection when needed.
        this.socket.setSoTimeout(5000);
        this.input = new BufferedReader(new InputStreamReader(socketConnection
                .getInputStream()));
        this.output = new BufferedWriter(new OutputStreamWriter(
                socketConnection.getOutputStream()));
        this.readerThread = new ReaderThread();
    }

    public void disconnect(String string) {
        write("DISC|" + string);
        try {
            socket.close();
            input.close();
            output.close();
            readerThread.shutdown();
        } catch (IOException e) {
        }
    }

    public void startReading(ConnectionCallback connectionCallback) {
        this.readerThread.start();
        this.onLine = connectionCallback;
    }

    public void write(String line) {
        try {
            output.write(line);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    private class ReaderThread extends Thread {
        private boolean running = true;

        public ReaderThread() {
            super("RemoteServer-Reader");
            this.setDaemon(true);
        }

        public void shutdown() {
            running = false;
        }

        @Override
        public void run() {
            String line = null;
            while (running) {
                try {
                    line = input.readLine();
                    if (line == null) {
                        if (!running) {
                            break;
                        }
                    } else {
                        onLine.onLine(line);
                    }
                } catch (IOException e) {
                }
            }
            Connection.this.onLine = null;
        }
    }

    public interface ConnectionCallback {
        void onLine(String line);
    }

}
