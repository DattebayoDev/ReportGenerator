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

    const startTime = performance.now();

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
        const endTime = performance.now();
        const processingTime = ((endTime - startTime) / 1000).toFixed(2);

        loadingDiv.classList.add('hidden');

        // Populate result card
        populateResultCard(data, url, archetype, processingTime);

        errorDiv.classList.add('hidden');
        resultDiv.classList.remove('hidden');
    })
    .catch((error) => {
        console.error('Error:', error);
        loadingDiv.classList.add('hidden');
        resultDiv.classList.add('hidden');
        errorDiv.classList.remove('hidden');
        errorDiv.innerHTML = `<p><strong>Error:</strong> ${error.message}</p>`;
    });
});

function populateResultCard(data, url, archetype, processingTime) {
    // Populate URL
    const resultUrl = resultDiv.querySelector('.result-url');
    resultUrl.textContent = url;

    // Populate title (use video title if available, otherwise use generic title)
    const resultTitle = resultDiv.querySelector('.result-title');
    resultTitle.textContent = data.title || 'Video Analysis';

    // Populate archetype tags
    const archetypesDiv = resultDiv.querySelector('.archetypes');
    archetypesDiv.innerHTML = '';
    const tag = document.createElement('span');
    tag.className = 'archetype-tag';
    tag.textContent = archetype.replace('_', ' ');
    archetypesDiv.appendChild(tag);

    // Populate content
    const resultContent = resultDiv.querySelector('.result-content');
    resultContent.innerHTML = '';

    // Clean up markdown and parse summary
    const summary = (data.summary || '').replaceAll('**', '');
    const lines = summary.split('\n');

    let currentList = null;

    lines.forEach(line => {
        const trimmedLine = line.trim();

        if (!trimmedLine) {
            // Empty line - close any open list
            if (currentList) {
                resultContent.appendChild(currentList);
                currentList = null;
            }
            return;
        }

        // Check if line is a bullet point
        if (trimmedLine.startsWith('- ') || trimmedLine.startsWith('• ')) {
            const bulletText = trimmedLine.substring(2).trim();

            // Create list if it doesn't exist
            if (!currentList) {
                currentList = document.createElement('ul');
            }

            const li = document.createElement('li');
            li.textContent = bulletText;
            currentList.appendChild(li);
        } else {
            // Close any open list before adding paragraph
            if (currentList) {
                resultContent.appendChild(currentList);
                currentList = null;
            }

            // Regular paragraph
            const p = document.createElement('p');
            p.textContent = trimmedLine;
            resultContent.appendChild(p);
        }
    });

    // Close any remaining open list
    if (currentList) {
        resultContent.appendChild(currentList);
    }

    // Populate footer with processing time
    const resultFooter = resultDiv.querySelector('.result-footer');
    resultFooter.textContent = `Processed in ${processingTime} seconds`;
}
