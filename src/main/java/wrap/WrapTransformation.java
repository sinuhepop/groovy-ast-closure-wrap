package wrap;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class WrapTransformation extends AbstractASTTransformation {

	@Override
	public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
		if (astNodes != null) {
			callToHandler((MethodNode) astNodes[1]);
		}
	}

	protected void callToHandler(MethodNode method) {

		method.getParameters()[0].setClosureSharedVariable(true);

		ClosureExpression body = new ClosureExpression( //

				// new Parameter[] { new Parameter(ClassHelper.OBJECT_TYPE, "stringParam") }, //
				Parameter.EMPTY_ARRAY,
				// new Parameter[0],
				// method.getParameters(),
				// null, //

				method.getCode());

		VariableScope scope = new VariableScope(method.getVariableScope());
		// method.getVariableScope().getDeclaredVariables().forEach((k, v) -> {
		// scope.putDeclaredVariable(v);
		// });
		body.setVariableScope(scope);

		MethodCallExpression callExp = new MethodCallExpression( //
				VariableExpression.THIS_EXPRESSION, //
				"handleWrapped", //
				new ArgumentListExpression(body));

		BlockStatement block = new BlockStatement();
		block.addStatement(new ExpressionStatement(callExp));
		method.setCode(block);
	}

}