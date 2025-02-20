import matplotlib.pyplot as plt

"""
###################################################
DEFINE YOUR PARAMETERS HERE
###################################################
"""
# Define your output file name and extension
file_name = "trial_2_p11"  # Do not include the file extension
file_extension = ".png"  # File extension

# Define your device type
device = "tablet" #set this to "tablet" or "phone"

# Define your point real-world diameter
point_size_mm = 9  # Desired point size in mm

# Graph parameters
x_axis_label = "Goals"
y_axis_label = "Assists"
title = "Players' Assists vs Goals for Three Teams"
x_axis_min = 0
x_axis_max = 100
y_axis_min = 0
y_axis_max = 100
grid_color = 'black'

# Define custom data for 3 series, each with 5 points
# Replace these lists with your desired coordinates (percentages of X)
x = [0.1, 0.2, 0.1, 0.1, 0.2,  # Series 1 x-coordinates
     0.5, 0.5, 0.6, 0.2, 0.1, # Series 2 x-coordinates
     0.9, 0.6, 0.9, 0.5, 0.7]  # Series 3 x-coordinates
x = [x_axis_min + xi * (x_axis_max - x_axis_min) for xi in x] # Scale them for the graph

y = [0.5, 0.4, 0.8, 0.9, 0.7,  # Series 1 y-coordinates
     0.5, 0.7, 0.5, 0.1, 0.3, # Series 2 y-coordinates
     0.1, 0.2, 0.3, 0.25, 0.3]  # Series 3 y-coordinates
y = [y_axis_min + yi * (y_axis_max - y_axis_min) for yi in y] # Scale them for the graph

# Define the colors for ON and OFF for each series
color_off =        (254/255, 255/255, 255/255) # White, but not fully, to have the device distinguish it from the white background
color_on_series1 = (0/255, 254/255, 0/255) # Green
color_on_series2 = (0/255, 253/255, 0/255) # Slight Green (non-distinguishable to the human eye)
color_on_series3 = (0/255, 252/255, 0/255) # Slighter Green (non-distinguishable to the human eye)

# Define custom colors for each point
point_colors = [
    # Series 1 colors
    color_off,      
    color_off, 
    color_off,  
    color_off,      
    color_off,
    
    # Series 2 colors
    color_on_series2,  # ON
    color_off, 
    color_off, 
    color_off,      
    color_on_series2,  # ON
    
    # Series 3 colors
    color_off,  
    color_off, 
    color_off,  
    color_on_series3,  # ON. IMPOSTOR
    color_off  
]

# Define the device's dpi (OPTIONAL, but recommended if you know the correct value)
tablet_dpi = 340  # standard tablet DPI

"""
###################################################
AUTOMATED FROM THIS POINT ONWARD
###################################################
"""
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

# Plot each point individually with its assigned color
for i in range(len(x)):
    plt.scatter(x[i], y[i],
                color=point_colors[i], edgecolor='black',
                s=point_size, zorder=2)

# Add labels and title
plt.xlabel(x_axis_label)
plt.ylabel(y_axis_label)
plt.title(title)

# Setting axis limits manually to ensure 0 and 1 are at the edges
plt.xlim(x_axis_min, x_axis_max)
plt.ylim(y_axis_min, y_axis_max)

# Add grid and save the plot
plt.grid(True, color=grid_color)
plt.savefig(file_name + file_extension, transparent=False, bbox_inches='tight', dpi=tablet_dpi) 
plt.show()
