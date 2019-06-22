try:  
   app.secret_key = os.environ["APP_SECRET"]
except KeyError: 
   print("Environment variable APP_SECRET not set.")
   sys.exit(1)