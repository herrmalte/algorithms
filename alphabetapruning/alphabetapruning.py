DEPTH = 3 # defualt depth 3

def play(state):
    player = state.getPlayer()
    bestPossible = 0
    bestState = state
    for s in state.nextPossibleStates():
        score = alphabeta(s, player, float('-inf'), float('inf'), DEPTH)
        if score > bestPossible:
            score = BestPossible
            bestState = s
    return bestState

def alphabeta(state, player, alpha, beta, DEPTH):
    
    if state.isTerminal():
        return state.utilityFunction()
    elif DEPTH == 0:
        return state.heuristicFunction()

    if player == 'MAX':
        v = float('-inf')
        for s in state.nextPossibleStates():
            v = max( v, alphabeta( s, alpha, beta, 'MIN', DEPTH-1))
            alpha = max(v, alpha)
            if beta <= alpha:
                break
    elif player == 'MIN':
        v = float('inf')
        for s in state.nextPossibleStates():
            v  = min( v, alphabeta( s, alpha, beta, 'MAX', DEPTH-1))
            beta = min( v, beta)
            if beta <= alpha:
                break
    return v


