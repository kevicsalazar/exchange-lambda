FROM public.ecr.aws/lambda/java:21

COPY app/build/classes/kotlin/main ${LAMBDA_TASK_ROOT}

CMD [ "handlers.save_user_info.Handler::handleRequest" ]