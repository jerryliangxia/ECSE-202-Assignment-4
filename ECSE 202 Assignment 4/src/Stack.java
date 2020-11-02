
//stack class
//@author frank ferrie

public class Stack {
	//names the listNode top
	listNode top;
	//push method is used to add elements to the stack
	public void push(String token)
	{
		//initializes a first new listNode which is in the form of a string
		listNode newTop = new listNode(token);
		//first listNode created points to the second listNode
		newTop.next = top;
		top = newTop;
	}
	
	//pop method used to remove from top of stack
	public String pop()
	{
		// if the stack is empty (top == null)
		if(top == null)
		{
			//null is returned
			return null;
		}
		//else wise
		else
		{
			//the element at the top of the stack 
			//is changed for the element which was previously in second place
			listNode oldtop = top;
			top = top.next;
			//returns the value stored at the top of the stack
			return oldtop.value;
		}
	}
	
}