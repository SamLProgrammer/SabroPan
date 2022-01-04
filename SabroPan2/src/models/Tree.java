package models;

import java.util.ArrayList;
import java.util.Comparator;

import observers.TablePrinter;

public class Tree<T> {

	private Node<T> rootNode;
	private Comparator<T> dataComparator;
	private TablePrinter<T> tablePrinter;

	public Tree(Comparator<T> dataComparator, TablePrinter<T> tablePrinter) {
		this.dataComparator = dataComparator;
		this.tablePrinter = tablePrinter;
	}

	// ================================== UPPER METHODS
	// =================================

	public void showTree() {
		belowShowTree(rootNode);
	}

	public Node<T> createNode(T data) {
		return new Node<T>(data);
	}

	public void addNode(Node<T> node) {
		belowAddNode(rootNode, node);
		balanceTreeAfterAdding();
	}

	public Node<T> findNode(Node<T> node) {
		return belowFindNode(rootNode, node);
	}

	public Node<T> findNodeByData(T data) {
		return belowFindNodeByData(rootNode, data);
	}

	public void deleteNodeByData(T data) {
		deleteNode(belowFindNodeByData(rootNode, data));
		balanceTreeAfterAdding();
	}

	public void deleteNode(Node<T> node) {
		if (node != null) {
			belowDeleteNode2(findNode(node), 1);
		}
	}

	public Node<T> findFatherNode(Node<T> node) {
		Node<T> auxNode = new Node<T>();
		if (node == rootNode) {
			auxNode = rootNode;
		} else {
			auxNode = belowFindFatherNode(rootNode, node);
		}

		return auxNode;
	}

	public void lightTree(int i) {
		inOrderLightTree(rootNode, i);
	}

	public void balanceTreeAfterAdding() {
		balanceTreeAfterAdding(rootNode);
	}
	
	public int size() {
		return countNodes(rootNode);
	}

	public int countLevels() {
		int levels = 0;
		if (rootNode != null) {
			levels = countTreeLevels(rootNode, 1);
		}
		return levels;
	}

	// =============================== BELLOW ESSENTIAL METHODS
	// =============================

	public Node<T> belowFindNode(Node<T> fatherNode, Node<T> node) { // probado, full
		if (dataComparator.compare(node.getData(), fatherNode.getData()) == 0) {
			return fatherNode;
		} else {
			if (fatherNode.getLeftNode() != null && belowFindNode(fatherNode.getLeftNode(), node) != null) {
				return belowFindNode(fatherNode.getLeftNode(), node);
			}
			if (fatherNode.getRightNode() != null && belowFindNode(fatherNode.getRightNode(), node) != null) {
				return belowFindNode(fatherNode.getRightNode(), node);
			} else {
				return null;
			}
		}
	}

	public Node<T> belowFindNodeByData(Node<T> node, T data) { // probado, full
		Node<T> auxNode = null;
		if (node != null) {
			if (dataComparator.compare(data, node.getData()) < 0) {
				auxNode = belowFindNodeByData(node.getLeftNode(), data);
			}
			else if(dataComparator.compare(data, node.getData()) > 0) {
				auxNode = belowFindNodeByData(node.getRightNode(), data);
			}
			else {
				auxNode = node;
			}
		}
		return auxNode;
	}

	public Node<T> belowFindFatherNode(Node<T> fatherNode, Node<T> node) { // probado, full
		if (!fatherNode.isLeave() && sonEqualsToNode(fatherNode, node) != null) {
			return fatherNode;
		} else {
			if (fatherNode.getLeftNode() != null && belowFindFatherNode(fatherNode.getLeftNode(), node) != null) {
				return belowFindFatherNode(fatherNode.getLeftNode(), node);
			}
			if (fatherNode.getRightNode() != null && belowFindFatherNode(fatherNode.getRightNode(), node) != null) {
				return belowFindFatherNode(fatherNode.getRightNode(), node);
			} else {
				return null;
			}
		}
	}

	public void belowAddNode(Node<T> fatherNode, Node<T> node) { // probado, full
		if (fatherNode == null) {
			rootNode = node;
		} else {
			if (dataComparator.compare(node.getData(), fatherNode.getData()) > 0) {
				if (fatherNode.getRightNode() == null) {
					fatherNode.setRightNode(node);
				} else {
					belowAddNode(fatherNode.getRightNode(), node);
				}
			} else {
				if (fatherNode.getLeftNode() == null) {
					fatherNode.setLeftNode(node);
				} else {
					belowAddNode(fatherNode.getLeftNode(), node);
				}
			}
		}
	}

	public void belowDeleteNode2(Node<T> node, int aja) { // probado funciona
		if (node.isLeave()) {
			if (node == rootNode) {
				rootNode = null;
			} else {
				Node<T> father = findFatherNode(node);
				if (sonEqualsToNode(father, node).equals(father.getLeftNode())) {
					father.setLeftNode(null);
				} else {
					father.setRightNode(null);
				}
			}
		} else if (node.getLeftNode() == null || node.getRightNode() == null) {
			if (node.getLeftNode() == null) {
				if (node.equals(rootNode)) {
					rootNode = node.getRightNode();
				} else {
					Node<T> father = findFatherNode(node);
					if (father.getLeftNode() != null && father.getLeftNode().equals(node)) {
						father.setLeftNode(node.getRightNode());
					} else {
						father.setRightNode(node.getRightNode());
					}
				}
			} else {
				if (node.equals(rootNode)) {
					rootNode = node.getLeftNode();
				} else {
					Node<T> father = findFatherNode(node);
					if (father.getLeftNode() != null && father.getLeftNode().equals(node)) {
						father.setLeftNode(node.getLeftNode());
					} else {
						father.setRightNode(node.getLeftNode());
					}
				}
			}
		} else {
			node.setData(leftestNode(node.getRightNode()).getData());
			belowDeleteNode2(leftestNode(node.getRightNode()), 1);
		}
	}

	// ================================= BALANCING METHODS
	// =================================

	public void balanceTreeAfterAdding(Node<T> node) {
		if (node != null) {
			balanceTreeAfterAdding(node.getLeftNode());
			balanceTreeAfterAdding(node.getRightNode());
			if (balanceFactor(node) <= -2) {
				leftBalanceSubTreeAfterAdding(node);
			}
			if (balanceFactor(node) >= 2) {
				rightBalanceSubTreeAfterAdding(node);
			}
		}
	}

	public void rightBalanceSubTreeAfterAdding(Node<T> node) {
		if (rootNode != node) {
			if (balanceFactor(node.getLeftNode()) == -1 || balanceFactor(node.getLeftNode()) == 0) {
				Node<T> fatherNode = findFatherNode(node);
				Node<T> auxNode = node.getLeftNode().getRightNode();
				Node<T> auxNode2 = node.getLeftNode();
				node.getLeftNode().setRightNode(auxNode.getLeftNode());
				node.setLeftNode(auxNode.getRightNode());
				auxNode.setRightNode(node);
				auxNode.setLeftNode(auxNode2);
				if (fatherNode.getRightNode() == node) {
					fatherNode.setRightNode(auxNode);
				} else {
					fatherNode.setLeftNode(auxNode);
				}
				balanceTreeAfterAdding(auxNode);
			} else {
				Node<T> fatherNode = findFatherNode(node);
				Node<T> auxNode = node.getLeftNode();
				node.setLeftNode(auxNode.getRightNode());
				auxNode.setRightNode(node);
				if (fatherNode.getRightNode() == node) {
					fatherNode.setRightNode(auxNode);
				} else {
					fatherNode.setLeftNode(auxNode);
				}
				balanceTreeAfterAdding(auxNode);
			}
		} else {
			if (balanceFactor(node.getLeftNode()) == -1 || balanceFactor(node.getLeftNode()) == 0) {
				Node<T> auxNode = node.getLeftNode().getRightNode();
				Node<T> auxNode2 = node.getLeftNode();
				node.getLeftNode().setRightNode(auxNode.getLeftNode());
				node.setLeftNode(auxNode.getRightNode());
				auxNode.setRightNode(node);
				auxNode.setLeftNode(auxNode2);
				rootNode = auxNode;
				balanceTreeAfterAdding(auxNode);
			} else {
				Node<T> auxNode = node.getLeftNode();
				node.setLeftNode(auxNode.getRightNode());
				auxNode.setRightNode(node);
				rootNode = auxNode;
				balanceTreeAfterAdding(auxNode);
			}
		}
	}

	public void leftBalanceSubTreeAfterAdding(Node<T> node) {
		if (rootNode != node) {
			if (balanceFactor(node.getRightNode()) == 1 || balanceFactor(node.getRightNode()) == 0) {
				Node<T> fatherNode = findFatherNode(node);
				Node<T> auxNode = node.getRightNode().getLeftNode();
				Node<T> auxNode2 = node.getRightNode();
				node.getRightNode().setLeftNode(auxNode.getRightNode());
				node.setRightNode(auxNode.getLeftNode());
				auxNode.setLeftNode(node);
				auxNode.setRightNode(auxNode2);
				if (fatherNode.getRightNode() == node) {
					fatherNode.setRightNode(auxNode);
				} else {
					fatherNode.setLeftNode(auxNode);
				}
				balanceTreeAfterAdding(auxNode);
			} else {
				Node<T> fatherNode = findFatherNode(node);
				Node<T> auxNode = node.getRightNode();
				node.setRightNode(auxNode.getLeftNode());
				auxNode.setLeftNode(node);
				if (fatherNode.getRightNode() == node) {
					fatherNode.setRightNode(auxNode);
				} else {
					fatherNode.setLeftNode(auxNode);
				}
				balanceTreeAfterAdding(auxNode);
			}
		} else {
			if (balanceFactor(node.getRightNode()) == 1 || balanceFactor(node.getRightNode()) == 0) {
				Node<T> auxNode = node.getRightNode().getLeftNode();
				Node<T> auxNode2 = node.getRightNode();
				node.getRightNode().setLeftNode(auxNode.getRightNode());
				node.setRightNode(auxNode.getLeftNode());
				auxNode.setLeftNode(node);
				auxNode.setRightNode(auxNode2);
				rootNode = auxNode;
				balanceTreeAfterAdding(auxNode);
			} else {
				Node<T> auxNode = node.getRightNode();
				node.setRightNode(auxNode.getLeftNode());
				auxNode.setLeftNode(node);
				rootNode = auxNode;
				balanceTreeAfterAdding(auxNode);
			}
		}
	}

	// ================================== UTILITY METHODS
	// ===================================

	public int countTreeLevels(Node<T> node, int counter) {
		int levels = counter;
		int auxLevel;
		if (!node.isLeave()) {
			if (node.getLeftNode() != null) {
				levels = countTreeLevels(node.getLeftNode(), counter + 1);
			}
			if (node.getRightNode() != null
					&& (auxLevel = countTreeLevels(node.getRightNode(), counter + 1)) > levels) {
				levels = auxLevel;
			}
		}
		return levels;
	}

	public int balanceFactor(Node<T> node) {
		int left = 0, right = 0;
		if (!node.isLeave()) {
			if (node.getLeftNode() != null) {
				left = countTreeLevels(node.getLeftNode(), 1);
			}
			if (node.getRightNode() != null) {
				right = countTreeLevels(node.getRightNode(), 1);
			}
		}
		return left - right;
	}

	public Node<T> sonEqualsToNode(Node<T> fatherNode, Node<T> node) {
		Node<T> auxNode = new Node<T>();
		if (fatherNode.getLeftNode() != null && fatherNode.getLeftNode().equals(node)) {
			auxNode = fatherNode.getLeftNode();
		} else if (fatherNode.getRightNode() != null && fatherNode.getRightNode().equals(node)) {
			auxNode = fatherNode.getRightNode();
		} else {
			auxNode = null;
		}
		return auxNode;
	}

	public Node<T> leftestNode(Node<T> node) {
		if (node.getLeftNode() == null) {
			return node;
		} else {
			return leftestNode(node.getLeftNode());
		}
	}

	public void belowShowTree(Node<T> node) {
		if (node != null) {
			belowShowTree(node.getLeftNode());
			System.out.println(node.getData());
			belowShowTree(node.getRightNode());
		}
	}

	public void inOrderLightTree(Node<T> node, int i) {
		if (node != null && i < 0) {
			inOrderLightTree(node.getLeftNode(), i - 1);
			node.setPainted(true);
			inOrderLightTree(node.getRightNode(), i - 1);
		}
	}

	public boolean balancingTest() {
		boolean flag = false;
		if (isTreeBalanced(rootNode) == 0 && countNodes(rootNode) == 20) {
			flag = true;
		}
		return flag;
	}

	public int isTreeBalanced(Node<T> node) {
		if (node == null) {
			return 0;
		}
		if (balanceFactor(node) > 1 || balanceFactor(node) < -1) {
			return 1;
		} else {
			return isTreeBalanced(node.getLeftNode()) + isTreeBalanced(node.getRightNode());
		}
	}

	public int countNodes(Node<T> node) {
		if (node == null) {
			return 0;
		}
		if (node.isLeave()) {
			return 1;
		} else {
			return countNodes(node.getRightNode()) + countNodes(node.getLeftNode()) + 1;
		}
	}
	
	public void sendDataToTable(Node<T> node) {
		if(node != null) {
			sendDataToTable(node.getLeftNode());
			tablePrinter.addRowToTable(node.getData());
			sendDataToTable(node.getRightNode());
		}
	}

	public boolean nodeExists(ArrayList<Node<T>> list, T data) {
		boolean flag = false;
		for (Node<T> node : list) {
			if (dataComparator.compare(node.getData(), data) == 0) {
				flag = true;
			}
		}
		return flag;
	}

	// ================================== GETTERS && SETTERS
	// ================================

	public Node<T> getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node<T> rootNode) {
		this.rootNode = rootNode;
	}

	public Comparator<T> getComparator() {
		return dataComparator;
	}

	public TablePrinter<T> getTablePrinter() {
		return tablePrinter;
	}

	public void sendDataToTable() {
		sendDataToTable(rootNode);
	}
}
