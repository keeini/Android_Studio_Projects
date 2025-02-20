import matplotlib.pyplot as plt
import numpy as np

file_name = "trial_1_s9"  # Do not include the file extension
file_extension = ".png"  # File extension

# Establishing point size in typographic points based on real-life dimensions
device = "tablet" #set this to "tablet" or "phone"
point_size_mm = 5  # Desired point size in mm (before Y-axis correction)

#typographic point size calculation 
tablet_dpi = 340  # standard DPI

if device == "tablet" :
     plt.figure(figsize=(10,6.5)) # Estimate in inches for landscape tablet screen dimensions
elif device == "phone" :
     # Start figure object and define size
    plt.figure(figsize=(6,3)) # Estimate in inches for landscape phone screen dimensions
else :
     print ("Please define device type as 'tablet' or 'phone'.")
point_size_in = point_size_mm / 25.4  # Convert mm to inches
point_size_typo = point_size_in * 72  # Convert inches to typographic points
point_size = point_size_typo ** 2  # Final point size in typographic points (square of typo points)

# Define custom data for 3 series, each with 5 points
# Replace these lists with your desired coordinates
x = [0.9, 0.9, 0.8, 0.7, 0.9,  # Series 1 x-coordinates
     0.5, 0.5, 0.6, 0.2, 0.1, # Series 2 x-coordinates
     0.1, 0.2, 0.1, 0.1, 0.2]  # Series 3 x-coordinates
y = [0.1, 0.9, 0.8, 0.2, 0.8,  # Series 1 y-coordinates
     0.5, 0.7, 0.5, 0.1, 0.3, # Series 2 y-coordinates
     0.5, 0.4, 0.8, 0.9, 0.7]  # Series 3 y-coordinates

# Define custom colors for each point
point_colors = [
    # Series 1 colors
    (255/255, 0/255, 0/255),        #red      
    (254/255, 0/255, 0/255),        #slight red
    (254/255, 255/255, 255/255),  
    (254/255, 255/255, 255/255),      
    (254/255, 255/255, 255/255),
    
    # Series 2 colors
    (253/255, 0/255, 0/255),        #slighter red  
    (253/255, 255/255, 255/255), 
    (253/255, 255/255, 255/255), 
    (253/255, 255/255, 255/255),      
    (253/255, 255/255, 255/255),  
    
    # Series 3 colors
    (252/255, 255/255, 255/255),  
    (252/255, 255/255, 255/255), 
    (252/255, 255/255, 255/255),  
    (252/255, 255/255, 255/255), 
    (252/255, 255/255, 255/255)  
]

# Plot each point individually with its assigned color
for i in range(len(x)):
    plt.scatter(x[i], y[i],
                color=point_colors[i], edgecolor='black',
                s=point_size, zorder=2)

# Add labels and title
plt.xlabel('X axis')
plt.ylabel('Y axis')
plt.title('15-Point Scatter Plot with 3 Series')

# Setting axis limits manually to ensure 0 and 1 are at the edges
plt.xlim(0, 1)
plt.ylim(0, 1)

# Add grid and save the plot
plt.grid(True, color='black')
plt.savefig(file_name + file_extension, transparent=False, bbox_inches='tight', dpi=tablet_dpi) 
plt.show()
