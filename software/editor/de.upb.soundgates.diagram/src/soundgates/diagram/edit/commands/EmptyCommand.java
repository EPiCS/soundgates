package soundgates.diagram.edit.commands;

import org.eclipse.gef.commands.Command;

public class EmptyCommand extends Command{
	@Override
	public boolean canExecute() {
		return false;
	}
}
