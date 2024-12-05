package org.example

class MulComputer {
    private data class ComputeContext(var mulEnabled: Boolean = true, var product: Int = 0)

    fun compute(nodes: List<AstNode>, toggleMuls: Boolean = false): Int =
        if (toggleMuls) computeAll(nodes)
        else computeMuls(nodes)

    private fun computeMuls(nodes: List<AstNode>): Int =
        nodes
            .filterIsInstance<MulNode>()
            .sumOf { it.leftFactor.value * it.rightFactor.value }

    private fun computeAll(nodes: List<AstNode>): Int =
        nodes
            .fold(ComputeContext()) { ctx, node ->
                when (node) {
                    is DoNode -> ctx.mulEnabled = true
                    is DontNode -> ctx.mulEnabled = false
                    is MulNode -> if (ctx.mulEnabled) ctx.product += node.leftFactor.value * node.rightFactor.value
                }
                ctx
            }
            .product
}