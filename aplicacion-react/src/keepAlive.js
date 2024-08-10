document.addEventListener('DOMContentLoaded', function() {
    const keepAliveUrl = 'https://actividades-spring-react.onrender.com';
    function keepSessionAlive() {
      fetch(keepAliveUrl, { method: 'GET', credentials: 'include' })
        .then(response => {
          if (!response.ok) {
            console.error('Failed to keep session alive');
          }
        })
        .catch(error => {
          console.error('Error keeping session alive:', error);
        });
    }
    setInterval(keepSessionAlive, 300000);
  }
);