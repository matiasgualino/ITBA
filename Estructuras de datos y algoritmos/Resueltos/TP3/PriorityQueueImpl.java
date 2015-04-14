package TP3;

public class PriorityQueueImpl<T> implements PriorityQueue<T>{

    PriorityNode<T> first;

    public PriorityQueueImpl(){
    this.first=null;
    }

    @Override
    public void enqueue(T elem, int priority) {
        PriorityNode<T> aux = new PriorityNode<T>(elem, priority, null);
        PriorityNode<T> current = first;
        PriorityNode<T> previous = null;
        /*Empty queue case*/
        if(first==null){
            first=aux;
            return;
        }
        /*Add at beginning case*/
        if(first.priority<priority){
            aux.next=first;
            first=aux;
            return;
        }
        /*Add in the middle case*/
        previous=current;
        current=current.next;
        while(current!=null){
            if(current.priority>=priority){
                previous=current;
                current=current.next;
            }
            else{
                previous.next = aux;
                aux.next=current;
                return;
            }
        }
        /*Add at last case*/
        previous.next=aux;
        return;
    }

    @Override
    public T dequeue() {
        if(first==null)
            return null;
        T aux = first.value;
        first=first.next;
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return first==null;
    }

    private static class PriorityNode<T>{
        int priority;
        T value;
        PriorityNode<T> next;

        public PriorityNode(T value, int priority,PriorityNode<T> next){
            this.priority=priority;
            this.value=value;
            this.next=next;
        }
    }
}
