try:
    try:
        raise Exception("me")      
    finally:
        raise Exception("you")
except Exception, e:
    print e
          
     