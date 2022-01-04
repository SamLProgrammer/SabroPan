package models;

public class Node<T> {
	
	private T data;
	
	private boolean painted;
	
	private Node<T> leftNode;
	
	private Node<T> rightNode;

	public Node(T data) {
		setData(data);
	}
	
	public Node() {
		
	}
	
	public boolean isLeave() {
		boolean flag = true;
		if(leftNode != null || rightNode != null) {
			flag = false;
		}
		return flag;
	}
	
	public T getData() {
		return data;
	}

	public Node<T> getLeftNode() {
		return leftNode;
	}

	public Node<T> getRightNode() {
		return rightNode;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setLeftNode(Node<T> leftNode) {
		this.leftNode = leftNode;
	}

	public void setRightNode(Node<T> rightNode) {
		this.rightNode = rightNode;
	}

	public boolean isPainted() {
		return painted;
	}

	public void setPainted(boolean painted) {
		this.painted = painted;
	}

}
