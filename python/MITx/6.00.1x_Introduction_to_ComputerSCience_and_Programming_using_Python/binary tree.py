class binaryTree(object):
    '''creating binary tree'''
    def __init__(self, value):
        self.value = value
        self.leftBranch = None
        self.rightBranch = None
        self.parent = None
    def setLeftBrach(self, node):
        self.leftBranch = node
    def setRightBranch(self, node):
        self.rightBranch = node
    def setParent(self, parent):
        self.parent = parent
# and other methods
    def getValue(self):
        return self.value
    def getLeftBranch(self):
        return self.leftBranch
    def getRightBranch(self):
        return self.rightBranch
    def getParent(self):
        return self.parent
    def __str__(self):
        return str(self.value)        
# Let's build a binary tree
n1 = binaryTree(1)
n2 = binaryTree(2)
n3 = binaryTree(3)
n4 = binaryTree(4)
n5 = binaryTree(5)
n6 = binaryTree(6)
n7 = binaryTree(7)
n8 = binaryTree(8)

n5.setLeftBrach(n2)
n2.setParent(n5)
n5.setRightBranch(n8)
n8.setParent(n5)
n2.setLeftBrach(n1)
n1.setParent(n2)
n2.setRightBranch(n4)
n4.setParent(n2)
n8.setLeftBrach(n6)
n6.setParent(n8)
n6.setRightBranch(n7)
n7.setParent(n6)
n4.setLeftBrach(n3)
n3.setParent(n4)

def find6(node):
    '''checks if the value of node is equal to 6'''
    print 'at node ',node.getValue()
    return node.getValue() == 6
    
def DFSBinary(root, fcn):
    '''depth first search for binary trees'''
    stack = [root]
    while len(stack) > 0:
        if fcn(stack[0]):
            return True
        else:
            temp = stack.pop(0)
            if temp.getRightBranch():
                stack.insert(0, temp.getRightBranch())
            if temp.getLeftBranch():
                stack.insert(0, temp.getLeftBranch())                             
    return False
    
def BFSBinary(root, fcn):
    """breadth first search for trees"""
    queue = [root]
    while len(queue) > 0:
        if fcn(queue[0]):
            return True
        else:
            temp = queue.pop(0)
            if temp.getLeftBranch():
                queue.append(temp.getLeftBranch())
            if temp.getRightBranch():
                queue.append(temp.getRightBranch())
    return False                                        

print "search algos for trees"                
print DFSBinary(n5, find6)                               
print BFSBinary(n5, find6)

def DFSBinaryPath(root, fcn):
    """DFS for trees, in additional it tracks the path to get to the number"""
    stack = [root]
    while len(stack) > 0:
        if fcn(stack[0]):
            return TracePath(stack[0])
        else:
            temp = stack.pop(0)
            if temp.getRightBranch():
                stack.insert(0, temp.getRightBranch())
            if temp.getLeftBranch():
                stack.insert(0, temp.getLeftBranch())                             
    return False
    
def TracePath(node):
    #function to track path
    if not node.getParent():
        return [node]
    else:
        return [node] + TracePath(node.getParent())

print "DFS binary search which tracks the path"
foo = DFSBinaryPath(n5, find6)
print [e.getValue() for e in foo]

def lt6(node):
    """check if the value of node is less then the thing that we are looking for"""
    return node.getValue() > 6

def DFSBinaryOrdered(root, fcn, ltFcn):
    """DFS for ordered trees"""
    stack = [root]
    while len(stack) > 0:
        if fcn(stack[0]):
            return True                        
        elif ltFcn(stack[0]):
            temp = stack.pop(0)
            if temp.getLeftBranch():
                stack.insert(0, temp.getLeftBranch())
        else:
            temp = stack.pop(0)
            if temp.getRightBranch():
                stack.insert(0, temp.getRightBranch())                             
    return False                                              

print "DFS binary ordered search"
print DFSBinaryOrdered(n5, find6, lt6)

def buildDTree(sofar, todo):
    """build a decision tree
    sofar : is a list which includes all the items i have selected so far as the left branch 
    of tree
    todo : is a list of things from which i have to make a dicision to include it or not
    in the list as the right branch of tree"""
    if len(todo) == 0:
        return binaryTree(sofar)
    else:
        #including item in sofar list to build tree
        withelt = buildDTree(sofar + [todo[0]], todo[1:])
        #not including item in sofar list
        withoutelt = buildDTree(sofar, todo[1:])
        here = binaryTree(sofar)
        here.setLeftBrach(withelt)
        here.setRightBranch(withoutelt)
        return here

a = [6, 3]
b = [7, 2]
c = [8, 4]
d = [9, 5]
                
testTree = buildDTree([], [a, b, c, d])     
    
def sumValues(lst):
    """function to return the sum of values"""
    vals = [e[0] for e in lst]
    return sum(vals)
    
def WeightBelow10(lst):
    """function to check if the sum of weights is less then 10"""
    wts = [e[1] for e in lst]
    return sum(wts) <= 10

def DFSDTree(root, valueFcn, constraintFcn):
    """DFS decision tree"""
    stack = [root]
    best = None
    visited = 0
    while len(stack) > 0:
        visited += 1
        if constraintFcn(stack[0].getValue()):
            if best == None:
                best = stack[0]
            elif valueFcn(stack[0].getValue()) > valueFcn(best.getValue()):
                best = stack[0]
                print best.getValue()
            temp = stack.pop(0)
            if temp.getRightBranch():
                stack.insert(0, temp.getRightBranch())
            if temp.getLeftBranch():
                stack.insert(0, temp.getLeftBranch())
        else:
            stack.pop(0)
    print 'visited ',visited
    return best

def BFSDTree(root, valueFcn, constraintFcn):
    """BFS decision tree"""
    queue = [root]
    best = None
    visited = 0
    while len(queue) > 0:
        visited += 1
        if constraintFcn(queue[0].getValue()):
            if best == None:
                best = queue[0]
            elif valueFcn(queue[0].getValue()) > valueFcn(best.getValue()):
                best = queue[0]
                print best.getValue()
            temp = queue.pop(0)
            if temp.getLeftBranch():
                queue.append(temp.getLeftBranch())
            if temp.getRightBranch():
                queue.append(temp.getRightBranch())
        else:
            queue.pop(0)
    print 'visited ',visited
    return best

print "solutions for decision trees"
boo = DFSDTree(testTree, sumValues, WeightBelow10)
ooo = BFSDTree(testTree, sumValues, WeightBelow10)
print boo.getValue()
print ooo.getValue()

def atLeast15(lst):
    return sumValues(lst) >= 15

def DFSDTreeGoodEnough(root, valueFcn, constraintFcn, stop):
    """better DFS decision tree"""
    stack = [root]
    best = None
    visited = 0
    while len(stack) > 0:
        visited += 1
        if constraintFcn(stack[0].getValue()):
            if best == None:
                best = stack[0]
            elif valueFcn(stack[0].getValue()) > valueFcn(best.getValue()):
                best = stack[0]
            if stop(best.getValue()):
                print 'visited ',visited
                return best    
            temp = stack.pop(0)
            if temp.getRightBranch():
                stack.insert(0, temp.getRightBranch())
            if temp.getLeftBranch():
                stack.insert(0, temp.getLeftBranch())
        else:
            stack.pop(0)
    print 'visited ',visited
    return best

def BFSDTreeGoodEnough(root, valueFcn, constraintFcn, stop):
    """better BFS decision tree"""
    queue = [root]
    best = None
    visited = 0
    while len(queue) > 0:
        visited += 1
        if constraintFcn(queue[0].getValue()):
            if best == None:
                best = queue[0]
            elif valueFcn(queue[0].getValue()) > valueFcn(best.getValue()):
                best = queue[0]
            if stop(best.getValue()):
                print 'visited ',visited
                return best    
            temp = queue.pop(0)
            if temp.getLeftBranch():
                queue.append(temp.getLeftBranch())
            if temp.getRightBranch():
                queue.append(temp.getRightBranch())
        else:
            queue.pop(0)
    print 'visited ',visited
    return best

print "better version of decision trees, it check at every node if the value is satisfactory"
print "DFSDTreeGoodEnough"
print DFSDTreeGoodEnough(testTree, sumValues, WeightBelow10, atLeast15)
print "BDSDTreeGoodEnough"
print BFSDTreeGoodEnough(testTree, sumValues, WeightBelow10, atLeast15)

def DTImplicit(toConsider, avail):
    """building decision tree on the fly"""
    #return value of solution and solution
    if toConsider == [] or avail == 0:
        result = (0, ())
    elif toConsider[0][1] > avail:
        result = DTImplicit(toConsider[1:], avail)
    else:
        nextItem = toConsider[0]
        withVal, withToTake = DTImplicit(toConsider[1:], avail-nextItem[1])
        withVal += nextItem[0]
        withoutVal, withoutToTake = DTImplicit(toConsider[1:], avail)
        if withVal > withoutVal:
            result = (withVal, withToTake + (nextItem,))
        else:
            result = (withoutVal, withoutToTake)
    return result

print "implicit decision tree, last decision trees were building the whole tree \
and then searching the solution but this one build the tree on the fly"
stuff = [a, b, c, d]
val, taken = DTImplicit(stuff, 10)
print val
print taken

def DFSBinaryNoLoop(root, fcn):
    """this function deals with loop in our tree"""
    stack = [root]
    seen = []
    while len(stack) > 0:
        if fcn(stack[0]):
            return True
        else:
            temp = stack.pop(0)
            seen.append(temp)
            if temp.getRightBranch():
                if not temp.getRightBranch() in seen:
                    stack.insert(0, temp.getRightBranch())
            if temp.getLeftBranch():
                if not temp.getLeftBranch() in seen:
                    stack.insert(0, temp.getLeftBranch())                             
    return False

n3.setLeftBrach(n5)
n5.setParent(n3)
print "dealing with loops"
print DFSBinaryNoLoop(n5, find6)              