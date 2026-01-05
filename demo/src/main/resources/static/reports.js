let allReports = [];

window.onload = function() {
    loadReports();
    setupFilterListeners();
};

function loadReports() {
    fetch('/reports')
        .then(response => response.json())
        .then(data => {
            allReports = data;
            applyFilter();
        })
        .catch(error => {
            console.error('Error fetching reports:', error);
        });
}

function setupFilterListeners() {
    const filterRadios = document.querySelectorAll('input[name="archetypeFilter"]');
    filterRadios.forEach(radio => {
        radio.addEventListener('change', applyFilter);
    });
}

function applyFilter() {
    const selectedFilter = document.querySelector('input[name="archetypeFilter"]:checked').value;
    console.log(selectedFilter);
    console.log(allReports)
    let filteredReports;
    if (selectedFilter === 'ALL') {
        filteredReports = allReports;
    } else {
        filteredReports = allReports.filter(report => report.archetype == selectedFilter);
    }

    displayReports(filteredReports);
}

function displayReports(reports) {
    const loadingDiv = document.getElementById('loading-reports');
    const table = document.getElementById('reportsTable');
    const tableBody = document.getElementById('reportTableBody');

    // Hide loading, show table
    loadingDiv.classList.add('hidden');
    table.classList.remove('hidden');

    tableBody.innerHTML = '';

    reports.forEach(report => {
        const row = document.createElement('tr');

        // Construct URL from postId and platform
        let url;
        if (report.platform === 'YOUTUBE') {
            url = `https://youtube.com/watch?v=${report.postId}`;
        } else if (report.platform === 'REDDIT') {
            url = `https://reddit.com/r/${report.postId}`;
        }

        // Format timestamp
        const timestamp = new Date(report.timestamp).toLocaleString();

        row.innerHTML = `
            <td><a href="${url}" target="_blank">${url}</a></td>
            <td>${report.archetype}</td>
            <td>${report.summary}</td>
            <td>${timestamp}</td>
        `;

        tableBody.appendChild(row);
    });

    if (reports.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4">No reports found. Analyze some content first!</td></tr>';
    }
}
