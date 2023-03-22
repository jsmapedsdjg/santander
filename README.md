how to use:

run Main class. and use jconsole to send messages and receive prices

quircks:
1. usage of big decimal. Most venues use longs in this case, but since we need to apply
margins in percents and not in pips, I don't want to implement div operation. 
2. This is not low-latency or high-performance code
3. JMX are used as substitute for rest services since implementing proper messaging queue and 
rest endpoints in 2 hours is not feasible.
4. 