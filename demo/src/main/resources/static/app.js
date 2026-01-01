const form = document.getElementById('analyze-form');
const urlInput = document.getElementById('url-input');
const resultDiv = document.getElementById('result');
const errorDiv = document.getElementById('error');
const customPromptTextarea = document.getElementById('custom-prompt');

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

    console.log('Submitting URL:', url);
    console.log('Archetype:', archetype);
    console.log('Custom Prompt:', customPrompt);

    fetch('/analyze', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
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
        summary = (data.summary).split('\n');
        resultDiv.textContent = summary;
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
