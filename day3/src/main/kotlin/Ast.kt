package org.example

abstract class AstNode {
    override operator fun equals(other: Any?): Boolean =
        when (other) {
            is MulNode -> this is MulNode && this.leftFactor == other.leftFactor && this.rightFactor == other.rightFactor
            is MulFactorNode -> this is MulFactorNode && this.value == other.value
            is DoNode -> this is DoNode
            is DontNode -> this is DontNode
            else -> false
        }
}

class MulNode(val leftFactor: MulFactorNode, val rightFactor: MulFactorNode) : AstNode()

class MulFactorNode(val value: Int) : AstNode()

class DoNode : AstNode()

class DontNode : AstNode()
