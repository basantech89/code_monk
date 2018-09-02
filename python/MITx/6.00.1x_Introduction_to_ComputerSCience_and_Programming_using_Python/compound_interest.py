balance = 5000.0
annualInterestRate = 18.0
monthlyPaymentRate = 2.0
total_paid = 0.0
amount = balance
remaining_balance = balance   
for i in range(1,12):
    minimum_payment = amount*monthlyPaymentRate/100
    unpaid_balance = amount - minimum_payment
    interest = unpaid_balance*annualInterestRate/(100*12)
    amount = unpaid_balance+interest
    total_paid += minimum_payment
    remaining_balance -= minimum_payment
    print 'Month: '+str(i)
    print 'Minimum monthly payment: ',round(minimum_payment,2)
    print 'Remaining balance: ',round(remaining_balance,2)

print 'Total paid: ',round(total_paid,2)
print 'Remaining balance: ',round(remaining_balance,2)                
        