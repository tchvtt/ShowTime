document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.scrollable-div').forEach(div => {
        let isDragging = false;
        let startX;
        let scrollLeft;

        // Mouse scroll
        div.addEventListener('wheel', function (event) {
            if (event.deltaY !== 0) {
                event.preventDefault();
                const scrollSpeed = 2;
                this.scrollLeft += event.deltaY * scrollSpeed;
            }
        });
        

        /*
        // Drag-scroll
        div.addEventListener('mousedown', (e) => {
            isDragging = true;
            div.classList.add('dragging');
            startX = e.pageX - div.offsetLeft;
            scrollLeft = div.scrollLeft;
        });

        div.addEventListener('mouseleave', () => {
            isDragging = false;
            div.classList.remove('dragging');
        });

        div.addEventListener('mouseup', () => {
            isDragging = false;
            div.classList.remove('dragging');
        });

        div.addEventListener('mousemove', (e) => {
            if (!isDragging) return;
            e.preventDefault();
            const x = e.pageX - div.offsetLeft;
            const walk = (x - startX) * 2;
            div.scrollLeft = scrollLeft - walk;
        });
        */

    });
});
