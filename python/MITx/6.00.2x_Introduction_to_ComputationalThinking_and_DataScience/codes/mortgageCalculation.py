import pylab
def findPayment(loan, r, m):
    """Assumes: loan and r are floats, m an int
    Returns the monthly payment for a mortage of
    size loan and a monthly rate of r  for m months"""
    return loan*((r*(1+r)**m)/((1+r)**m-1))

class MortgagePlots(object):
    
    def plotPayments(self, style):
        pylab.plot(self.paid[1:], style, label = self.legend)
        
    def plotTotPd(self, style):
        totPd = [self.paid[0]]
        for i in range(1, len(self.paid)):
            totPd.append(totPd[-1] + self.paid[i])
        pylab.plot(totPd, style, label = self.legend)
    
class Mortgage(MortgagePlots,object):
    """Abstract class for building diiferent kinds of mortgages"""
    def __init__(self, loan, annRate, months):
        """Create a new mortgage"""
        self.loan = loan
        self.rate = annRate/12
        self.months = months
        self.paid = [0.0]
        self.owed = [loan]
        self.payment = findPayment(loan, self.rate, months)
        self.legend = None #discriptor of mortage
        
    def makePayment(self):
        """Make a payment"""
        self.paid.append(self.payment)
        reduction = self.payment - self.owed[-1]*self.rate
        self.owed.append(self.owed[-1] - reduction)
        
    def getTotalPaid(self):
        """Return the total amount paid so far"""
        return sum(self.paid)                                                                  

    def __str__(self):
        return self.legend

#fixed-rate mortgage
class Fixed(Mortgage):
    def __init__(self, loan, r, months):
        Mortgage.__init__(self, loan, r, months)
        self.legend = "Fixed, " + str(r*100) + "%"

#fixed-rate mortgage with up-front points
class FixedWithPts(Fixed):
    def __init__(self, loan, r, months, pts):
        Fixed.__init__(self, loan, r, months)
        self.pts = pts
        self.paid = [loan*(pts/100)]
        self.legend += ", " + str(pts) + " points"
        
#mortgage that changes interest rate after 48 months
class TwoRate(Mortgage):
    def __init__(self, loan, r, months, teaserRate, teaserMonths):
        Mortgage.__init__(self, loan, teaserRate, months)
        self.teaserMonths = teaserMonths
        self.teaserRate = teaserRate
        self.nextRate = r/12.0
        self.legend = str(teaserRate*100) + " % for "+ str(self.teaserMonths)+ " months, then " + str(r*100) + "%"
    def makePayment(self):
        if len(self.paid) == self.teaserMonths + 1:
            self.rate = self.nextRate
            self.payment = findPayment(self.owed[-1], self.rate, self.months - self.teaserMonths)
        Mortgage.makePayment(self)

def plotMortgages(morts, amt):
    styles = ['b-', 'r-', 'g:']
    payments = 0
    cost = 1
    pylab.figure(payments)
    pylab.title('Monthly payments of diiferent $' + str(amt) + 'Mortgages')
    pylab.xlabel('Months')
    pylab.ylabel('Monthly Payments')
    pylab.figure('cost')
    pylab.title('Cost of different $' + str(amt) + 'Mortgages')
    pylab.xlabel('Months')
    pylab.ylabel('Total Payments')
    for i in range(len(morts)):
        pylab.figure(payments)
        morts[i].plotPayments(styles[i])
        pylab.figure(cost)
        morts[i].plotTotPd(styles[i])
    pylab.figure(payments)
    pylab.legend(loc = 'upper center')
    pylab.figure(cost)
    pylab.legend(loc = 'best')
    pylab.show()
    

#helper function to try out alternative
def compareMortgages(amt, years, fixedRate, pts, ptsRate, varRate1, varRate2, varMonths):
    #create alternative mortgages
    totMonths = years*12
    fixed1 = Fixed(amt, fixedRate, totMonths)
    fixed2 = FixedWithPts(amt, ptsRate, totMonths, pts)
    twoRate = TwoRate(amt, varRate2, totMonths, varRate1, varMonths)
    morts = [fixed1, fixed2, twoRate]
    
    #run experiment
    for m in range(totMonths):
        for mort in morts:
            mort.makePayment()
    
    #report results
    for m in morts:
        print m
        print "Total Payment = $" + str(int(m.getTotalPaid()))

    plotMortgages(morts, amt)        
# amount borrowed: $200,000
# term: 30 years
# option 1: rate = 7 %
# option 2: 3.25 points upfront, rate = 5 %
# option 3: 48 months of rate = 5 %, then rate = 9.5 %                                                                                                                                                                        

compareMortgages(amt=200000, years=30, fixedRate=0.07, pts=3.25, ptsRate=0.05, varRate1=0.045, varRate2=0.095, varMonths=48)               