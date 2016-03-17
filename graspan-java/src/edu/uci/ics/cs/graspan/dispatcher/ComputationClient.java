package edu.uci.ics.cs.graspan.dispatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import edu.uci.ics.cs.graspan.computation.Engine;
import edu.uci.ics.cs.graspan.support.GraspanLogger;

public class ComputationClient {

	private static final Logger logger = GraspanLogger
			.getLogger("ComputationClient");

	public static void main(String args[]) throws IOException {

		String baseFilename = args[0];

		/*
		 * Scan the edge destination counts file
		 */
		BufferedReader computationConfigStream = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File(baseFilename))));
		String ln;

		String[] tok;
		while ((ln = computationConfigStream.readLine()) != null) {
			tok = ln.split(" ");
			if (tok[0].compareTo("INPUT_GRAPH_FILEPATH") == 0) {
				GlobalParams.setBasefilename(tok[2]);
			}
			if (tok[0].compareTo("TOTAL_NUM_PARTS") == 0) {
				GlobalParams.setNumParts(Integer.parseInt(tok[2]));
			}
			if (tok[0].compareTo("RELOAD_PLAN") == 0) {
				GlobalParams.setReloadPlan(tok[2]);
			}
			if (tok[0].compareTo("EDC_SIZE") == 0) {
				GlobalParams.setEdcSize(Integer.parseInt(tok[2]));
			}
			if (tok[0].compareTo("OP_EDGE_TRACKER_INTERVAL") == 0) {
				GlobalParams.setOpEdgeTrackerInterval(Integer.parseInt(tok[2]));
			}
			if (tok[0].compareTo("MAX_PART_SIZE_POST_NEW_EDGES") == 0) {
				GlobalParams.setPartMaxPostNewEdges(Integer.parseInt(tok[2]));
			}
			if (tok[0].compareTo("NEW_EDGE_NODE_SIZE") == 0) {
				GlobalParams.setNewEdgesNodeSize(Integer.parseInt(tok[2]));
			}
		}

		computationConfigStream.close();

		logger.info("Starting computation.");
		logger.info("Total number of partitions: " + GlobalParams.getNumParts());
		logger.info("Number of parts per computation: "
				+ GlobalParams.getNumPartsPerComputation());
		logger.info("Reload plan: " + GlobalParams.getReloadPlan());

		Engine engine = new Engine();
		engine.run();
		logger.info("FINISHED.");
	}
}