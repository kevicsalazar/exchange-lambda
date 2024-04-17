FROM gradle:8.4-jdk21 AS build

COPY --chown=gradle:gradle . /appBuild
WORKDIR /appBuild
RUN gradle -p handlers/${MODULE_NAME} build --no-daemon

FROM public.ecr.aws/lambda/java:21
COPY --from=build /appBuild/handlers/${MODULE_NAME}/build/libs/*.jar ${LAMBDA_TASK_ROOT}/lambda.jar
CMD [ "handlers.${MODULE_NAME}.Handler::handleRequest" ]
