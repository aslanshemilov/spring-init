/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bench;

import com.example.init.InitApplication;
import com.example.manual.ManualApplication;

import org.junit.jupiter.api.Test;

import org.springframework.init.bench.CaptureSystemOutput;
import org.springframework.init.bench.CaptureSystemOutput.OutputCapture;
import org.springframework.init.bench.ProcessLauncherState;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 *
 */
@CaptureSystemOutput
public class ProcessLauncherStateTests {

	@Test
	public void select(OutputCapture output) throws Exception {
		// System.setProperty("bench.args", "-verbose:class");
		ProcessLauncherState state = new ProcessLauncherState(InitApplication.class,
				"target", "--server.port=0");
		state.addArgs("-Dspring.functional.enabled=false");
		state.setProfiles("slim");
		state.before();
		state.run();
		state.after();
		assertThat(output.toString()).contains("Benchmark app started");
		assertThat(output.toString()).doesNotContain("/actuator");
	}

	@Test
	public void vanilla(OutputCapture output) throws Exception {
		// System.setProperty("bench.args", "-verbose:class");
		ProcessLauncherState state = new ProcessLauncherState(ManualApplication.class,
				"target", "--server.port=0");
		// state.setProfiles("actr");
		state.before();
		state.run();
		state.after();
		assertThat(output.toString()).contains("Benchmark app started");
		assertThat(output.toString()).doesNotContain("/actuator");
	}

	@Test
	public void actr(OutputCapture output) throws Exception {
		// System.setProperty("bench.args", "-verbose:class");
		ProcessLauncherState state = new ProcessLauncherState(ManualApplication.class,
				"target", "--server.port=0");
		state.setProfiles("actr");
		state.before();
		state.run();
		state.after();
		assertThat(output.toString()).contains("Benchmark app started");
		assertThat(output.toString()).contains("/actuator");
	}

}