package com.michael.api;

/**
 * Created By: Michael Risher
 * Date: 10/7/15
 * Time: 4:25 PM
 */
public class Linklist {
	private Node head;
	private Node worker;

	public Linklist( int data ) {
		head = new Node();
		this.head.data = data;
	}

	public void append( int data ) {
		if ( head != null ) {
			Node last = new Node();
			worker = head;
			while ( ( worker.next ) != null ){
				last = worker;
			}
			Node node = new Node( data );
			node.prev = last;
			last.next = node;
		} else {
			Node node = new Node( data );
			head = node;
		}
	}

	@Override
	public String toString() {
		if( head != null ) {
			worker = head;
			String out = "";
			do{
				out += "data element -> " + worker.data + "\n";
			} while( ( worker = worker.next ) != null );
			return out;
		}
		return "";
	}

	private class Node {
		public Node prev = null;
		public Node next = null;
		public int data;

		private Node( int data ) {
			this.data = data;
		}

		private Node() {
		}
	}


}
