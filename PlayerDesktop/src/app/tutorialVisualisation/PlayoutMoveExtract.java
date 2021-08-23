package app.tutorialVisualisation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import game.Game;
import manager.Manager;
import manager.Referee;
import other.AI;
import other.GameLoader;
import other.context.Context;
import other.move.Move;
import other.trial.Trial;
import utils.LudiiAI;
import utils.RandomAI;

public class PlayoutMoveExtract{

	public static List<String> generateTrialFile(final Manager manager, final String gamePath, final int i) {

		final File gameFile = new File(gamePath);

		// Create the trial
		System.out.println("Game " + i);
		final Game game = GameLoader.loadGameFromFile(gameFile);

		final Trial trial = new Trial(game);
		final Context context = new Context(game, trial);

		game.start(context);

		final List<AI> ai_players = new ArrayList<AI>();

		final AI ai1 = new LudiiAI();
		final AI ai2 = new RandomAI();

		ai1.initAI(game, 0);
		ai2.initAI(game, 1);

		ai_players.add(null);
		ai_players.add(ai1);
		ai_players.add(ai2);

		// Play a trial
		final Trial output = game.playout(context, ai_players, 1.0, null, 0, -1, ThreadLocalRandom.current());

		// Save the trial to a file for later use
		final String trialPath = "trials/test" + i + ".trl";
		final File trialFile = new File(trialPath);
		try {
			final Referee ref = manager.ref();
			List<String> gameOptionStrings = new ArrayList<>();

			if (ref.context().game().description().gameOptions() != null) {
				gameOptionStrings = ref.context().game().description().gameOptions().allOptionStrings
									(
											manager.settingsManager().userSelections().selectedOptionStrings()
									);
			}
			trial.saveTrialToTextFile(trialFile, gamePath, gameOptionStrings, manager.currGameStartRngState());
		} catch(final IOException e) {
			e.printStackTrace();
		}

		final List<Move> move_list = trial.generateCompleteMovesList();
		String moveListString = "";

		for (final Move move : move_list) {
//			System.out.println(move.toTrialFormat(context));
			if (move.mover() != 0) {
				if(move.actions().size() > 1) {
					moveListString += "Extra " + move.toEnglishString(context) + "\n";
//					System.out.print("Extra ");
//					System.out.println(move.toEnglishString(context));

				} else {
					moveListString += "Move " + move.toEnglishString(context) + "\n";
//					System.out.print("Move ");
//					System.out.println(move.toEnglishString(context));
				}
			}
		}
		System.out.println(context.winners() + "\n");		
		final List<String> generalizedMoveList = MoveListParser.toGeneralizedMoveList(moveListString);
		for (final String move: generalizedMoveList) {
			System.out.println(move);
		}
		return generalizedMoveList;
	}

}
