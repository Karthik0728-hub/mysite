/**
 * Demo Carousel Component JavaScript
 * Provides interactive functionality for the demo carousel
 */

(function() {
    'use strict';

    // Wait for DOM to be ready
    document.addEventListener('DOMContentLoaded', function() {
        const carousels = document.querySelectorAll('.cmp-demo-carousel');
        
        carousels.forEach(function(carousel) {
            initCarousel(carousel);
        });
    });

    function initCarousel(carousel) {
        const slides = carousel.querySelectorAll('.cmp-demo-carousel__slide');
        const slidesContainer = carousel.querySelector('.cmp-demo-carousel__slides');
        
        if (!slides.length || !slidesContainer) {
            return;
        }

        let currentSlide = 0;
        let isAutoPlaying = true;
        let autoPlayInterval;
        const autoPlayDelay = 5000; // 5 seconds
        
        // Get number of visible items based on screen size
        function getVisibleItems() {
            if (window.innerWidth <= 480) return 1;
            if (window.innerWidth <= 768) return 2;
            if (window.innerWidth <= 1200) return 3;
            return 4; // Desktop
        }
        
        let visibleItems = getVisibleItems();

        // Create indicators dynamically
        createIndicators(carousel, slides.length);

        // Start auto-play
        startAutoPlay();

        // Add event listeners
        setupEventListeners(carousel);

        function createIndicators(carousel, slideCount) {
            if (slideCount <= 1) return;

            const indicatorsContainer = document.createElement('div');
            indicatorsContainer.className = 'cmp-demo-carousel__indicators';
            
            for (let i = 0; i < slideCount; i++) {
                const indicator = document.createElement('button');
                indicator.className = 'cmp-demo-carousel__indicator';
                if (i === 0) {
                    indicator.classList.add('cmp-demo-carousel__indicator--active');
                }
                indicator.setAttribute('aria-label', `Go to slide ${i + 1}`);
                indicator.addEventListener('click', function() {
                    goToSlide(i);
                });
                indicatorsContainer.appendChild(indicator);
            }
            
            carousel.appendChild(indicatorsContainer);
        }

        function setupEventListeners(carousel) {
            // Navigation buttons
            const prevBtn = carousel.querySelector('.cmp-demo-carousel__nav--prev');
            const nextBtn = carousel.querySelector('.cmp-demo-carousel__nav--next');
            
            if (prevBtn) {
                prevBtn.addEventListener('click', function() {
                    console.log('Demo Carousel: Previous button clicked');
                    goToPreviousSlide();
                });
            }
            
            if (nextBtn) {
                nextBtn.addEventListener('click', function() {
                    console.log('Demo Carousel: Next button clicked');
                    goToNextSlide();
                });
            }

            // Indicators are created dynamically in createIndicators function

            // Pause on hover
            carousel.addEventListener('mouseenter', pauseAutoPlay);
            carousel.addEventListener('mouseleave', startAutoPlay);

            // Keyboard navigation
            carousel.addEventListener('keydown', function(e) {
                if (e.key === 'ArrowLeft') {
                    goToPreviousSlide();
                } else if (e.key === 'ArrowRight') {
                    goToNextSlide();
                } else if (e.key === ' ') {
                    e.preventDefault();
                    toggleAutoPlay();
                }
            });

            // Touch/swipe support
            let startX = 0;
            let endX = 0;

            carousel.addEventListener('touchstart', function(e) {
                startX = e.touches[0].clientX;
            });

            carousel.addEventListener('touchend', function(e) {
                endX = e.changedTouches[0].clientX;
                handleSwipe();
            });

            function handleSwipe() {
                const threshold = 50;
                const diff = startX - endX;

                if (Math.abs(diff) > threshold) {
                    if (diff > 0) {
                        goToNextSlide();
                    } else {
                        goToPreviousSlide();
                    }
                }
            }

            // Make carousel focusable for keyboard navigation
            carousel.setAttribute('tabindex', '0');

            // Handle window resize
            window.addEventListener('resize', function() {
                const newVisibleItems = getVisibleItems();
                if (newVisibleItems !== visibleItems) {
                    visibleItems = newVisibleItems;
                    // Reset to first slide when layout changes
                    currentSlide = 0;
                    updateSlidePosition();
                    updateIndicators();
                }
            });
        }

        function goToSlide(index) {
            if (index < 0 || index >= slides.length) return;
            
            currentSlide = index;
            updateSlidePosition();
            updateIndicators();
            resetAutoPlay();
        }

        function goToNextSlide() {
            const maxSlide = Math.max(0, slides.length - visibleItems);
            const nextIndex = Math.min(currentSlide + visibleItems, maxSlide);
            goToSlide(nextIndex);
        }

        function goToPreviousSlide() {
            const prevIndex = Math.max(0, currentSlide - visibleItems);
            goToSlide(prevIndex);
        }

        function updateSlidePosition() {
            // Calculate slide width based on visible items
            const translateX = -(currentSlide * (100 / visibleItems));
            slidesContainer.style.transform = `translateX(${translateX}%)`;
        }

        function updateIndicators() {
            const indicators = carousel.querySelectorAll('.cmp-demo-carousel__indicator');
            indicators.forEach(function(indicator, index) {
                if (index === currentSlide) {
                    indicator.classList.add('cmp-demo-carousel__indicator--active');
                } else {
                    indicator.classList.remove('cmp-demo-carousel__indicator--active');
                }
            });
        }

        function startAutoPlay() {
            if (slides.length <= 1) return;
            
            clearInterval(autoPlayInterval);
            autoPlayInterval = setInterval(function() {
                if (isAutoPlaying) {
                    goToNextSlide();
                }
            }, autoPlayDelay);
        }

        function pauseAutoPlay() {
            isAutoPlaying = false;
            clearInterval(autoPlayInterval);
        }

        function resetAutoPlay() {
            isAutoPlaying = true;
            startAutoPlay();
        }

        function toggleAutoPlay() {
            if (isAutoPlaying) {
                pauseAutoPlay();
            } else {
                startAutoPlay();
            }
        }

        // Initialize first slide
        updateSlidePosition();
    }
})();
