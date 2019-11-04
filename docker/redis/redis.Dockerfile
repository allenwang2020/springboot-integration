FROM redis:5.0.6-alpine

# Expose ports.
EXPOSE 6379
	
# Define default command.
CMD exec redis-server

