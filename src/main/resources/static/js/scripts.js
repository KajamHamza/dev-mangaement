document.addEventListener('DOMContentLoaded', () => {
    // Dark Mode Toggle
    const themeToggle = document.getElementById('themeToggle');
    const body = document.body;
    const savedTheme = localStorage.getItem('theme');

    if (savedTheme) {
        body.classList.add(savedTheme);
        if (themeToggle) {
            themeToggle.checked = savedTheme === 'dark-mode';
        }
    }

    themeToggle?.addEventListener('change', () => {
        body.classList.toggle('dark-mode');
        localStorage.setItem('theme', body.classList.contains('dark-mode') ? 'dark-mode' : 'light-mode');
        updateThemeIcon();
    });

    // Function to update theme icon
    function updateThemeIcon() {
        const themeIcon = document.querySelector('.theme-toggle [data-lucide]');
        if (body.classList.contains('dark-mode')) {
            themeIcon.setAttribute('data-lucide', 'moon');
        } else {
            themeIcon.setAttribute('data-lucide', 'sun');
        }
        lucide.createIcons();
    }

    // Initial icon update
    updateThemeIcon();

    // Smooth Scroll Effect for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });

    // Add animation to stats cards
    const statsCards = document.querySelectorAll('.stats .card');
    statsCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        setTimeout(() => {
            card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, 100 * index);
    });

    // Hover effect on project cards
    const projectCards = document.querySelectorAll('.project-card');
    projectCards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.transform = 'translateY(-5px)';
            card.style.boxShadow = '0 6px 12px rgba(98, 0, 238, 0.15)';
        });
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'translateY(0)';
            card.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)';
        });
    });

    // Dynamic Skill Management for Profile Page
    const skillsContainer = document.getElementById('skills');
    const addSkillBtn = document.getElementById('add-skill-btn');

    // Add new skill field
    addSkillBtn?.addEventListener('click', () => {
        const skillDiv = document.createElement('div');
        skillDiv.classList.add('skill-item');
        skillDiv.innerHTML = `
            <input type="text" name="skills" class="skill-input" placeholder="Enter skill" required>
            <button type="button" class="remove-skill-btn" onclick="removeSkill(this)">Remove</button>
        `;
        skillsContainer.appendChild(skillDiv);
    });

    // Function to remove skill field
    window.removeSkill = function(button) {
        button.parentElement.remove();
    };

    // Initialize Lucide icons
    lucide.createIcons();
});
