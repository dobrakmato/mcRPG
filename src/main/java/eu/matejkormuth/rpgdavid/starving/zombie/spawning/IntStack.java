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
package eu.matejkormuth.rpgdavid.starving.zombie.spawning;

public class IntStack {
    private int[] stack;
    private int top;

    /**
     * Creates a new IntStack with default starting capacity (16).
     */
    public IntStack() {
        this(16);
    }

    /**
     * Creates a new IntStack with specified starting capacity.
     * 
     * @param size
     *            starting capacity of stack
     */
    public IntStack(int size) {
        this.stack = new int[size];
        this.top = 0;
    }

    public void push(int obj) {
        if (this.top == stack.length) {
            this.doubleSize();
        }

        this.stack[this.top++] = obj;
    }

    public int pop() {
        return this.stack[--this.top];
    }

    public int[] exposeBackingArray() {
        return this.stack;
    }

    public int[] toArray() {
        int[] array = new int[this.top];
        System.arraycopy(this.stack, 0, array, 0, this.top);
        return array;
    }

    public int size() {
        return this.top;
    }
    
    public boolean isEmpty() {
        return this.top == 0;
    }

    /**
     * Doubles the size of backing array.
     */
    private void doubleSize() {
        int[] newStack = new int[this.stack.length * 2];
        System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
        this.stack = newStack;
    }
}