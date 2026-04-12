import java.io.*;
import java.util.*;
public class Main {
	static class Node{
		int val;
		Node left, right;
		Node(int val){
			this.val = val;
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int preIndex = 0;
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		while(T-- > 0){
			int N = Integer.parseInt(br.readLine());
			int[] preorder = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int[] inorder = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			//전위 순회 인덱스
			preIndex = 0;
			Node root = buildTree(0, inorder.length - 1, preorder, inorder);
			printPostOrder(root);
			System.out.println();
		}

	}

	static Node buildTree(int inStart, int inEnd, int[] preorder, int[] inorder){
		if (inStart > inEnd || preIndex >= preorder.length) return null;

		//전위 순회에서 루트 선택
		int rootVal = preorder[preIndex++];
		Node root = new Node(rootVal);

		//중위 순회에서 루트 위치 찾기
		int mid = findIndex(inorder, rootVal);

		//왼쪽 복원
		root.left = buildTree(inStart, mid-1, preorder, inorder);
		//오른쪽 복원
		root.right = buildTree(mid+1, inEnd, preorder, inorder);

		return root;
	}

	static int findIndex(int[] arr, int target){
		for(int i = 0; i<arr.length; i++){
			if(arr[i] == target) return i;
		}
		return -1;
	}

	static void printPostOrder(Node node){
		if(node == null) return;
		printPostOrder(node.left);
		printPostOrder(node.right);
		System.out.print(node.val+" ");
	}
}
//전위 - 루 > 왼 > 오
//중위 - 왼 > 루 > 오
//후위 - 왼 > 오 > 루
//최소단위의 트리를 구하고 조합?
/**
 * 3 2 1 4
 * 2 3 4 1
 * 2 4 1 3
 * */