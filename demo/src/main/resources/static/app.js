const form = document.getElementById('analyze-form');
const urlInput = document.getElementById('url-input');
const resultDiv = document.getElementById('result');
const errorDiv = document.getElementById('error');
const customPromptTextarea = document.getElementById('custom-prompt');
const loadingDiv = document.getElementById('loading');

// Show/hide custom prompt textarea based on radio selection
document.querySelectorAll('input[name="archetype"]').forEach(radio => {
    radio.addEventListener('change', function() {
        if (this.value === 'CUSTOM') {
            customPromptTextarea.classList.remove('hidden');
        } else {
            customPromptTextarea.classList.add('hidden');
        }
    });
});

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const url = urlInput.value;
    const archetype = document.querySelector('input[name="archetype"]:checked').value;
    const customPrompt = customPromptTextarea.value || '';

    // Show loading, hide previous results
    loadingDiv.classList.remove('hidden');
    resultDiv.classList.add('hidden');
    errorDiv.classList.add('hidden');
    fetch('/analyze', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'ngrok-skip-browser-warning': 'true'
        },
        body: JSON.stringify({
            url: url,
            archetype: archetype,
            customPrompt: customPrompt
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        loadingDiv.classList.add('hidden');

        // Clean up markdown and split by newlines
        const summary = (data.summary).replaceAll("**", "");
        const lines = summary.split('\n');

        // Clear existing content
        resultDiv.innerHTML = '';

        // Create a paragraph element for each line
        lines.forEach(line => {
            const p = document.createElement('p');
            p.textContent = line;
            resultDiv.appendChild(p);
        });

        errorDiv.classList.add('hidden');
        resultDiv.classList.remove('hidden');
    })
    .catch((error) => {
        console.error('Error:', error);
        resultDiv.classList.add('hidden');
        errorDiv.classList.remove('hidden');
        errorDiv.innerHTML = `<p>Error: ${error.message}</p>`;
    });


});
