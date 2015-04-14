package TP3;

import java.util.LinkedList;
import java.util.Queue;

public class StackQueue<T> {
	
	private Queue<T> stack1;
	private Queue<T> stack2;
	
	public StackQueue(){
		this.stack1=new LinkedList<T>();
		this.stack2=new LinkedList<T>();
	}
	
	public void enqueue(T elem){
		while(stack1.peek()!=null){
			stack2.add(stack1.poll());
		}
		stack2.add(elem);
	}
	
	public T dequeue(){
		while(stack2.peek()!=null){
			stack1.add(stack2.poll());
		}
		return stack1.poll();
	}
	
}
