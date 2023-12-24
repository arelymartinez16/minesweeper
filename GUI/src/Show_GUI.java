
public class Show_GUI {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Minesweeper_GUI ms = new Minesweeper_GUI();
			}
		});
	}

}
