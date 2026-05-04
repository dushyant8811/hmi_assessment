# UI Assessment Submission

Welcome to my submission for the Android UI Assessment. For this project, I chose to build the user interface entirely from scratch using **Jetpack Compose**, specifically avoiding third-party charting libraries to ensure pixel-perfect fidelity with the Figma designs.

Below is a breakdown of my development process, highlighting both my independent architectural work and how I utilized AI to assist with complex graphical mathematics.

## What I Built Independently
As a student, I took this opportunity to solidify my understanding of Android app architecture and state management:
*   **The Architecture & State:** I designed the `Scaffold` layout, the scrollable list structures, the data models, and the state logic (such as making the Monthly/Weekly toggle buttons fully functional).
*   **The UI System:** I coded the `InsightsCard` wrapper to ensure the specific drop shadows, spacing, and corner radii remained completely consistent across the entire application.
*   **Headers & Custom Icons:** I built the `InsightsHeader` and manually drew the custom 4-circle grid icon using standard Compose Canvas drawing functions.

## AI Collaboration & Complex UI Challenges
To match the exact specifications of the Figma design, I needed to implement some advanced Canvas drawing. I used AI (LLM) as a pair-programmer to help me navigate the complex math and custom modifiers required:

*   **Cycle Trends Chart:** Standard Compose bars couldn't cleanly handle the custom overlapping zones for menstruation and ovulation. I used AI to help me write the custom Canvas math to scale the bars dynamically based on the number of days.
*   **The Neumorphic Floating Footer:** Jetpack Compose natively only supports drop shadows, but the design required an inner shadow for a "carved" look. AI helped me build a custom `Modifier.innerShadow` using `drawWithContent` and `PathOperation.Difference`.
*   **Body Signals Donut Chart:** I built the rings and sweep gradients, but I needed AI's help with the trigonometry (`sin`/`cos`) to calculate the exact orbit positions for the floating percentage badges. AI also guided me to use `drawIntoCanvas` so the 3D shadows wouldn't get clipped by Compose's bounds.
*   **Curves & Gradients:** AI was incredibly helpful for calculating the smooth Bézier curves (`cubicTo`) on the Stability chart and writing the conditional logic (`i % 2 == 0`) to make the Lifestyle Impact pills alternate their gradient directions in a responsive grid.

Building these custom graphics taught me a massive amount about native drawing and modern UI development in Android. Thank you for reviewing my code!