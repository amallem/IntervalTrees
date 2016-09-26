
Implementation Details
-----------------------
`Language` : JAVA

#### Data Structure Overview
The data structure used to store all the Calendar events here is a self-balanced binary search tree(Red-Black tree) with 
a few additional properties each node.

- Every Node stores exactly one event, pointers to parent node, left & right sub-trees and the maximum endTime among 
all the events as `maxRightValue` in its right sub tree.
- The ordering of the nodes in the tree is based on the `startTime` of the events in the tree, i.e, an inorder traversal 
of the tree would give us all the events in the non-decreasing order of their start times.

I have used the Red-Black tree implementation to implement the self-balancing BST. The time complexities of some of the 
operations which can be performed on the tree are as follows:
- Insert : O(log n)
- Delete : O(log n)
- Search : O(log n)
- Max Height is log n

#### Memory 
Since each event is stored in one node of the red-black tree which takes a constant amount of memory we have a memory 
requirement of O(n). 
The leaf nodes of the red-black tree all should point to null but for implementation easiness I have created a custom 
`nullNode` - a common node to which all null pointers point in the tree. The left, right and parent nodes of the "nullNode" 
 is itself. This node does not affect the overall memory footprint of the data structure.

#### ADD
ADD method finds an empty spot for the new event in the self-balanced BST, attaches the event and rebalances the tree.
Duplicate events will get added as 2 separate events.

- Finding the free spot for insertion into the tree costs O(log n).
- Attaching the event to the tree takes O(1) time.
- Rebalancing takes constant time for performing rotation and since this can bubble up till the root, the upper bound on 
   rebalancing would be O(1 * logn) = O(log n).
    
 Therefore, an ADD operation would take a time equal to O(log n + 1 + log n) = `O(log n)`.   
    
#### QUERY
Since there is a chance that every event in the tree can overlap, our worst case complexity of the QUERY amounts to O(n).  
However the self-balances BST gives us some benefits while querying for all the events overlapping at a given point.

- If the given time point is larger than the "maxRightValue" stored in the current node then the entire subtree can be 
skipped.
- Similarly, if the given time point is smaller than the startTime of the current Node then it cannot have any overlapping
 events in the right subtree of the current node.
 
These benefits combined with the information that any point of time will have a maximum of 20 intersecting events reduces 
the query time to an amortized complexity of `O(log n + k)` where k is the number of events in the final answer.  

#### CLEAR
Since JAVA has the facility of garbage collection, we can clear the generated tree by just pointing the root node to the 
"nullNode". THis can be achieved in `O(1)` time. 

Build
------
Assuming that you are in the root folder of the application and have $JAVA_HOME configured in your env.
Compile ->
```
mkdir bin
javac -d bin -sourcepath src/ src/com/sourcegraph/Main.java
```
Run ->
```
cd bin
java com.sourcegraph.Main <Absolute filePath of input file>
```
