def create_preocesses(e):
    l = []
    l1 = []
    for i in range(len(e)):
        for j in range(e[i]):
            l1.append(j+1)
        l.append(l1)
        l1 = []
    return l


def execute_message(sender, receiver,processes):
    if not max(processes[receiver[0]][receiver[1]], processes[sender[0]][sender[1]] +1) == processes[receiver[0]][receiver[1]]:
        processes[receiver[0]][receiver[1]] = processes[sender[0]][sender[1]] + 1
        for i in range(receiver[1] +1,len(processes[receiver[0]])):
            processes[receiver[0]][i] = processes[receiver[0]][i-1] + 1
    return

def display(processes):
    display_time(processes)
    counter = 0
    for p in processes:
        print("Process {}: ".format(counter+1), end='')
        s ="\t"*p[0]+"e[{}]0".format(counter+1)
        for i in range(1,len(p)):
            s += "\t"*(p[i]-p[i-1]) + "e[{}]{}".format(counter+1, i)
        counter+= 1
        print(s)
        
def display_time(p):
    maximum = 0
    for i in p:
        if maximum < max(i):
            maximum = max(i)
    print("Time: \t",end='\t')
    for i in range(maximum):
        print(i,end='\t')
    print()

try:
    p = int(input("Enter number of processes: "))
    events = []
    for i in range(p):
        events.append(int(input("Enter number of events in process {}: ".format(i+1))))
    processes = create_preocesses(events)
    while True:
        c1 = int(input("\n\n**************************\n* 1. Send message        *\n* 2. Receive message     *\n* 3. Display time stamps *\n* 4. EXIT                *\n**************************\nEnter Your Choice: "))
        if c1 == 1:
            ps = int(input("Enter Process number of sender: "))
            ps = ps-1;
            es = int(input("Enter Event number of sender: "))
            pr = int(input("Enter Process number of Receiver: "))
            pr = pr-1;
            er = int(input("Enter Event number of Receiver: "))
            execute_message([ps,es],[pr,er],processes)
            print("Message sent from e[{}]{} to e[{}]{}".format(ps+1,es,pr+1,er))
            
        elif c1 == 2:
            pr = int(input("Enter Process number of Receiver: "))
            pr = pr-1;
            er = int(input("Enter Event number of Receiver: "))
            ps = int(input("Enter Process number of Sender: "))
            ps = ps-1;
            es = int(input("Enter Event number of Sender: "))
            execute_message([ps,es],[pr,er],processes)
            print("Message Received by e[{}]{}, sent from e[{}]{}".format(pr+1,er,ps+1,es))



        elif c1 == 3:
            display(processes)
        elif c1 ==4:
            print("Terminated...")
            break;
    exit(1)
except:
    print("Invalid Input !")
    exit(1)
