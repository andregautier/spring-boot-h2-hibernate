package com.myapi.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
  public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  /**
   * This method logs the execution time, the arguments and the result
   * of methods annotated with @LogExecution.
   *
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   */
  @Around("@annotation(com.myapi.logging.LogExecution)")
  public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    Object[] signatureArgs = proceedingJoinPoint.getArgs();

    // Get intercepted method details
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    // Measure method execution time
    StopWatch stopWatch = new StopWatch(className + "->" + methodName);
    stopWatch.start(methodName);
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();

    if (logger.isInfoEnabled()) {
      logger.info("==================================================");
      logger.info("Execution time");
      logger.info("==================================================");

      logger.info(stopWatch.prettyPrint());

      logger.info("==================================================");
      logger.info("Input");
      logger.info("==================================================");

      for (Object signatureArg: signatureArgs) {
        logger.info("INPUT - " + signatureArg);
      }

      logger.info("==================================================");
      logger.info("Output");
      logger.info("==================================================");

      logger.info("OUTPUT - " + result);
    }


    return result;
  }
}