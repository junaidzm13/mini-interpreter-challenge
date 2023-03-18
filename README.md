# CodeIT Suisse Kotlin Template

This project template provides some simple scaffolding for a level-based challenge.

All you need is to introduce implementations and configuration for:

* `LevelBasedChallenge`
* `ChallengeLevel`
* `ChallengeRequest`
* `ChallengeResponse`
* `Checker`
* `Iterable<ChallengeLevel>`

So that Spring can auto-wire them into `LevelEvaluatorService`.

## Explainer

An evaluation request coming in from the coordinator is modelled as an `EvaluationRequest`.

This `EvaluationRequest` can in turn be expressed as an implementation of `ChallengeRun`,
which can be `invoke()` on a `ChallengeRequest` to get a nullable `ChallengeRespose?` from the endpoint under
evaluation.

As such, the `ChallengeRequest` implementation should be something ready for Jackson to convert,
bearing in mind to use `@get:JsonIgnore` on any attributes you do not want to expose.

The implementation of `LevelBasedChallenge` is responsible for creating `ChallengeRequest` for a `ChallengeLevel`. 
Depending on where the logic for a request generation sits, this implementation can be simple, or harder.

Implementation of `ChallengeResponse` is self-explanatory, it models what your challenge is expecting in return.

Finally, the implementation of `Checker` is to score a given pair of `ChallengeRequest` and `ChallengeResponse`, 
returning a `ChallengeResult` for it.

## README.md

When this project is deployed, this very file will be copied to `BOOT-INF/classes/static` so
that [zero-md](https://zerodevx.github.io/zero-md/installation/) can render it in `index.html`.

So, remember to update this README.md and the title in `index.html` (search for `<!-- RENAME BELOW -->`).

## Local testing

For testing, write your own solver controller
(remember to run `App` with the environment variable `ENDPOINT_SUFFIX` defined).

Make sure your solver controller handles `POST` requests at the same path as `ENDPOINT_SUFFIX`.

Then, in your browser, go to `http://localhost:8080` (default port), open the console and run the following `fetch`
command:

````javascript
fetch('http://localhost:8080/evaluate', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ "runId": "test", "teamUrl": "http://localhost:8080/", "callbackUrl": "http://localhost:8080/coordinator" }),
})
.then(response => response.text())
.then(data => { console.log('Success!', data); })
.catch((error) => { console.error('Error:', error); });
````

## Example controller

It is strongly recommended to have a controller that serves an example of the challenge request/response.

By default, the endpoint `/example` is exempted from authentication requirements in production, so feel free to use
that.

The `GET` response for this can be something like:

````json
{
  "request": {
    "challenge": "request"
  },
  "response": {
    "challenge": [
      "response",
      "as",
      "desired"
    ]
  }
}
````

## Gitlab CI/Heroku

This template has Gitlab CI set up for easy deployments to Heroku via [`dpl`](https://github.com/travis-ci/dpl). By
default, only `master` branch will be deployed.

You will need to add the following CI/CD variables under Settings:

- `HEROKU_APP_NAME`
- `HEROKU_API_KEY` (Remember to set this variable as masked so that it will not be printed in build logs)

To perform deployments for branches, do not set the variables as protected so that the pipeline is able to access the
variables.

If you wish to have multiple environments, you can add in CI/CD variables with environment scope, and add a new job
in `.gitlab-ci.yml` extending `.deploy_template` for the new environment(s). A sample set-up can be found in
branch `gitlab-ci-demo`.
